package game;

import java.util.ArrayList;
import java.util.HashMap;

import entity.Dice;
import entity.Player;
import entity.FortuneCard.FortuneCard;
import game.Theme.*;


public class Turn {
	final char DICE_NUMBER = 8;
	
	private Player player;
	private FortuneCard card;
	private int scoreMultiplier;
	private int skullRerollCount;
	private Theme theme;
	private HashMap<Dice.Face, Integer> treasureInHand;
	private ArrayList<Dice> hand;
	private ArrayList<Dice> chest;
	
	public Turn(Player p) {
		this.player = p;
		initTurn();
	}
	
	private void initTurn() {
		scoreMultiplier = 1;
		skullRerollCount = 0;
		treasureInHand = new HashMap<Dice.Face,Integer>();
		for (Dice.Face f : Dice.Face.values()) {
			treasureInHand.put(f, 0);
		}
		for (int i = 0; i < DICE_NUMBER; i++) {
			hand.add(new Dice());
		}
		theme = new Normal();
	}
	
	public void setCard(FortuneCard c) {
		card = c;
		c.effect(this);
	}
	
	//Basic process
	public Boolean firstRoll() {
		int skulls = treasureInHand.get(Dice.Face.SKULL);
		for (Dice d : hand) {
			d.roll();
			if (d.getFace() == Dice.Face.SKULL) skulls++;
		}
		//Skull Island check
		if (skulls > 3 && theme.getClass() != game.Theme.SeaBattle.class) {
			theme = new SkullIsland();
		}
		return isDisqualified();
	}
	
	public Boolean reroll() {
		for (Dice d : hand) {
			d.roll();
		}
		return isDisqualified();
	}
	
	public OneTurnScoreChange endTurn() {
		OneTurnScoreChange delta;
		calculateChest();
		if (this.isDisqualified()) delta = theme.scoreCalculation(treasureInHand, this.card.getSkullsFromCard());
		else {
			calculateHand();
			delta = theme.scoreCalculation(treasureInHand, this.card.getSkullsFromCard());
		}
		delta.applyScoreMultiplier(scoreMultiplier);
		return delta;
	}
	
	//player action
	public Boolean moveToHand(ArrayList<Integer> index) {
		if (chest == null) return false;
		ArrayList<Dice> temp = new ArrayList<Dice>();
		for (int i : index) {
			if (i < 0 || i >= chest.size()) continue;
			Dice d = chest.get(i);
			temp.add(d);
		}
		for (Dice d : temp) chest.remove(d);
		hand.addAll(temp);
		return true;
	}
	
	public Boolean moveToChest(ArrayList<Integer> index) {
		if (chest == null) return false;
		ArrayList<Dice> temp = new ArrayList<Dice>();
		for (int i : index) {
			if (i < 0 || i >= hand.size()) continue;
			Dice d = hand.get(i);
			if(d.getFace() == Dice.Face.SKULL) continue;
			temp.add(d);
		}
		for(Dice d : temp) hand.remove(d);
		chest.addAll(temp);
		return true;
	}
	
	public Boolean lock(ArrayList<Integer> index) {
		for (int i : index) {
			if (i < 0 || i >= hand.size()) return false;
		}
		for(int i : index) {
			Dice d = hand.get(i);
			if (d.getFace() == Dice.Face.SKULL && !d.isLock()) skullRerollCount++;
			d.lock();
		}
		return true;
	}
	
	public Boolean unlock(ArrayList<Integer> index) {
		int skullWantToUnlock = 0;
		for (int i : index) {
			if (i < 0 || i >= hand.size()) return false;
			Dice d = hand.get(i);
			if (d.getFace() == Dice.Face.SKULL && d.isLock()) {
				skullWantToUnlock++;
				if (skullWantToUnlock > skullRerollCount) return false;
			}
		}
		for (int i : index) {
			Dice d = hand.get(i);
			if (d.getFace() == Dice.Face.SKULL && d.isLock()) skullRerollCount--;
			d.unlock();
		}
		return true;
	}
	
	//private method
	private Boolean isDisqualified() {
		int skulls = treasureInHand.get(Dice.Face.SKULL);
		for (Dice d : hand) {
			if (d.getFace() == Dice.Face.SKULL) skulls++;
			if (skulls >= 3 && theme.getClass() != SkullIsland.class) return true;
		}
		return false;
	}
	
	private void calculateChest() {
		if (chest == null) return;
		for(Dice d : chest) {
			int value = treasureInHand.get(d.getFace()) + 1;
			treasureInHand.put(d.getFace(), value);
		}
	}
	
	private void calculateHand() {
		for(Dice d : hand) {
			int value = treasureInHand.get(d.getFace()) + 1;
			treasureInHand.put(d.getFace(), value);
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
}
