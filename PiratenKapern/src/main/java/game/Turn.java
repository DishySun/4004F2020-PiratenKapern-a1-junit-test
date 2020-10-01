package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

import entity.Dice;
import entity.FortuneCard.FortuneCard;
import game.Theme.*;


public class Turn {
	final char DICE_NUMBER = 8;
	
	private FortuneCard card;
	private int scoreMultiplier;
	private int skullRerollCount;
	private Theme theme;
	private HashMap<Dice.Face, Integer> treasureInHand;
	private ArrayList<Dice> hand;
	private ArrayList<Dice> chest;
	private OneTurnScoreChange delta;
	private int perviousSkullCount;
	
	public Turn() {
		scoreMultiplier = 1;
		skullRerollCount = 0;
		perviousSkullCount = -1;
		treasureInHand = new HashMap<Dice.Face,Integer>();
		for (Dice.Face f : Dice.Face.values()) {
			treasureInHand.put(f, 0);
		}
		hand = new ArrayList<Dice>();
		for (int i = 0; i < DICE_NUMBER; i++) {
			hand.add(new Dice());
		}
		theme = new Normal();
	}
	
	//get methods
	public FortuneCard getFortuneCard() {return card;}
	public int getscoreMultiplier() {return scoreMultiplier;}
	public int getskullRerollCount() {return skullRerollCount;}
	public Theme getTheme() {return theme;}
	public HashMap<Dice.Face, Integer> getTreasureInHand(){return treasureInHand;}
	public ArrayList<Dice> getHand(){return hand;}
	public ArrayList<Dice> getChest(){return chest;}
	public OneTurnScoreChange getDelta() {return this.delta;}
	public String statString() {
		String result ="Hand: ";
		for (int i = 0 ; i < hand.size(); i++) {
			result += hand.get(i);
			if (i < hand.size()-1) result += ", ";
		}
		if (this.chest == null) return result;
		result += "\nTreasure Chest: ";
		for (int i = 0 ; i < chest.size(); i++) {
			result += chest.get(i);
			if (i < chest.size()-1) result += ", ";
		}
		return result;
	}
	
	public String chestString() {
		String result ="";
		for (int i = 0 ; i < hand.size(); i++) {
			result += chest.get(i);
			if (i < chest.size()-1) result += ", ";
		}
		return result;
	}
	
	//Basic process
	public void setCard(FortuneCard c) {
		card = c;
		c.effect(this);
	}

	public Boolean firstRoll() {
		int skulls = treasureInHand.get(Dice.Face.SKULL);
		for (Dice d : hand) {
			d.roll();
			if (d.getFace() == Dice.Face.SKULL) skulls++;
		}
		//Skull Island check
		if (skulls > 3 && !(theme instanceof game.Theme.SeaBattle)) {
			theme = new SkullIsland();
			this.perviousSkullCount = skulls;
		}
		return isDisqualified();
	}
	
	public int reroll() {
		//-1 - trying to roll less than 2 dice
		// 0 - continue turn
		// 1 - 3 or more skulls and disqualified from turn
		// 2 - didn't roll more skulls on skull island
		int rollCount = 0;
		for (Dice d: hand) {
			if (!d.isLock()) rollCount++;
		}
		if (rollCount < 2) return -1;
		int skulls = treasureInHand.get(Dice.Face.SKULL);
		if (rollCount >=2) {
			for (Dice d : hand) {
				d.roll();
				if (d.getFace() == Dice.Face.SKULL)skulls++;
			}
			if (this.theme instanceof game.Theme.SkullIsland) {
				if (skulls <= this.perviousSkullCount) return 2;
				else this.perviousSkullCount = skulls;
			}
		}
		if (isDisqualified()) return 1;
		else return 0;
	}
	
	public void endTurn() {
		this.calculateHand();
		this.calculateChest();
		delta = theme.scoreCalculation(treasureInHand, this.card.getSkullsFromCard());
		delta.applyScoreMultiplier(scoreMultiplier);
	}
	
	//player action
	public void moveToHand(HashSet<Integer> index) throws ChestException{
		if (chest == null) throw new ChestException("You don't have a Treasure Chest",1);
		if (index.size() > chest.size()) throw new ChestException("You only have " + chest.size() + " dice in your Treasure Chest and you are trying to move "+index.size()+" of them.",2);
		ArrayList<Dice> temp = new ArrayList<Dice>();
		for (int i : index) {
			if (i < 0 || i >= chest.size()) throw new ChestException("Invalid index: " + i,3);;
			Dice d = chest.get(i);
			temp.add(d);
		}
		for (Dice d : temp) chest.remove(d);
		hand.addAll(temp);
	}
	
	public void moveToChest(HashSet<Integer> index) throws ChestException{
		if (chest == null) throw new ChestException("You don't have a Treasure Chest",1);
		if (index.size() > hand.size()) throw new ChestException("You only have " + hand.size() + " dice in your hand and you are trying to move "+index.size()+" of them.",2);
		ArrayList<Dice> temp = new ArrayList<Dice>();
		for (int i : index) {
			if (i < 0 || i >= hand.size()) throw new ChestException("Invalid index: " + i , 3);
			Dice d = hand.get(i);
			if(d.getFace() == Dice.Face.SKULL) throw new ChestException("You are not allow to move a Skull to your Treasure Chest",4);
			temp.add(d);
		}
		for(Dice d : temp) hand.remove(d);
		chest.addAll(temp); 
	}
	
	public void lock(HashSet<Integer> index) {
		for(int i : index) {
			if (i < 0 || i >= hand.size()) continue;
			Dice d = hand.get(i);
			if (d.getFace() == Dice.Face.SKULL && !d.isLock()) skullRerollCount++;
			d.lock();
		}
	}
	
	public void unlock(HashSet<Integer> index) {
		for (int i : index) {
			if (i < 0 || i >= hand.size()) continue;
			Dice d = hand.get(i);
			if (d.getFace() == Dice.Face.SKULL && d.isLock())
				if (skullRerollCount > 0)skullRerollCount--;
				else continue;
			d.unlock();
		}
	}
	
	//private method
	private Boolean isDisqualified() {
		int skulls = treasureInHand.get(Dice.Face.SKULL);
		for (Dice d : hand) {
			if (d.getFace() == Dice.Face.SKULL) skulls++;
		}
		if (skulls < 3) return false;
		if (skulls == 3 && skullRerollCount == 1) return false;
		if (skulls >= 3 && theme instanceof SkullIsland) return false;
		return true;
	}
	
	private void calculateChest() {
		if (chest == null) return;
		for(Dice d : chest) {
			int value = treasureInHand.get(d.getFace()) + 1;
			treasureInHand.put(d.getFace(), value);
		}
	}
	
	private void calculateHand() {
		if (this.isDisqualified()) {
			int skulls = this.treasureInHand.get(Dice.Face.SKULL);
			for (Dice d : hand) {
				if (d.getFace() == Dice.Face.SKULL) skulls++;
			}
			for (Dice.Face f: this.treasureInHand.keySet()) {
				treasureInHand.put(f, 0);
			}
			treasureInHand.put(Dice.Face.SKULL,skulls);
		}else {
			for(Dice d : hand) {
				int value = treasureInHand.get(d.getFace()) + 1;
				treasureInHand.put(d.getFace(), value);
			}
		}
	}
	
	//fortune card methods
	public void enableChest() {
		chest = new ArrayList<Dice>();
	}
	
	public void captain() {
		scoreMultiplier = 2;
	}
	
	public void sorceress() {
		skullRerollCount = 1;
	}
	
	public void seaBattle(SeaBattle battle) {
		theme = battle;
	}
	
	public void gold() {
		int a = treasureInHand.get(Dice.Face.COIN) + 1;
		treasureInHand.put(Dice.Face.COIN, a);
	}
	
	public void diamond() {
		int a = treasureInHand.get(Dice.Face.DIAMOND) + 1;
		treasureInHand.put(Dice.Face.DIAMOND, a);
	}
	
	public void skull(int skullNumber) {
		int a = treasureInHand.get(Dice.Face.SKULL) + skullNumber;
		treasureInHand.put(Dice.Face.SKULL, a);
	}
	
	public void monkeyBusiness() {
		theme = new MonkeyBusiness();
	}
	
	//extra functions for extra functionalities
	//record & watch replay
	public Boolean firstRoll(ArrayList<Dice> dice) {
		int skulls = treasureInHand.get(Dice.Face.SKULL);
		this.hand = dice;
		for (Dice d : hand) {
			if (d.getFace() == Dice.Face.SKULL) skulls++;
		}
		//Skull Island check
		if (skulls > 3 && !(theme instanceof game.Theme.SeaBattle)) {
			theme = new SkullIsland();
			this.perviousSkullCount = skulls;
		}
		return isDisqualified();
	}
	
	public int reroll(ArrayList<Dice> dice) {
		//-1 - trying to roll less than 2 dice
		// 0 - continue turn
		// 1 - 3 or more skulls and disqualified from turn
		// 2 - didn't roll more skulls on skull island
		int rollCount = 0;
		for (Dice d: hand) {
			if (!d.isLock()) rollCount++;
		}
		if (rollCount < 2) return -1;
		for (int i = 0; i < dice.size();i++) {
			for (int j = 0; j < hand.size(); j++) {
				if (!hand.get(j).isLock()) {
					hand.set(j, dice.get(i));
					hand.get(j).lock();
					break;
				}
			}
		}
		int skulls = treasureInHand.get(Dice.Face.SKULL);
		if (rollCount >=2) {
			for (Dice d : hand) {
				if (d.getFace() == Dice.Face.SKULL)skulls++;
			}
			if (this.theme instanceof game.Theme.SkullIsland) {
				if (skulls <= this.perviousSkullCount) return 2;
				else this.perviousSkullCount = skulls;
			}
		}
		if (isDisqualified()) return 1;
		else return 0;
	}
}
