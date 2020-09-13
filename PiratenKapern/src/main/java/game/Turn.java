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
