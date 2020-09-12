package game;

import java.util.ArrayList;
import java.util.HashMap;

import entity.Dice;
import entity.Player;

public class Turn {
	private Player player;
	private int scoreMultiplier;
	private int skullRerollCount;
	private Boolean enableTreasureChest;
	private HashMap<Dice.Face, Integer> treasureInHand;
	private ArrayList<Dice> hand;
	private ArrayList<Dice> treasureChest;
}
