package entity.FortuneCard;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Collections;

public class Deck {
	private Stack<FortuneCard> deck;
	private ArrayList<FortuneCard> discardPile;
	
	private final int TWO_BATTLE_REWARD = 300;
	private final int THREE_BATTLE_REWARD = 500;
	private final int FOUR_BATTLE_REWARD = 1000;
	
	public Deck() {
		deck = new Stack<FortuneCard>();
		discardPile = new ArrayList<FortuneCard>();
		initDeck();
	}
	
	public void initDeck() {
		/*Breakdown of fortune cards:
			- 4 'gold coin'
			- 4 diamonds
			- 4 sorceress
			- 4 captain
			- 4 treasure chest
			- 4 monkey business (ie parrot-monkey card)
			- 3 1-skull cards
			- 2 2-skull cards
			- 2 2-sabre sea battle cards
			- 2 3-sabre sea battle cards
			- 2 4-sabre sea battle cards*/
		for (int i = 0; i < 4; i++) {
			deck.push(new Gold());
			deck.push(new Diamond());
			deck.push(new Sorceress());
			deck.push(new Captain());
			deck.push(new TreasureChest());
			deck.push(new MonkeyBusiness());
		}
		for (int i = 0; i < 3; i++) {
			deck.push(new Skull(1));
		}
		for (int i = 0; i < 2; i++) {
			deck.push(new Skull(2));
			deck.push(new SeaBattle(2,TWO_BATTLE_REWARD));
			deck.push(new SeaBattle(3,THREE_BATTLE_REWARD));
			deck.push(new SeaBattle(4,FOUR_BATTLE_REWARD));
		}
		shuffle();
	}
	
	private void shuffle() {
		Collections.shuffle(deck);
	}
	
	private void refresh() {
		deck.addAll(discardPile);
		discardPile.clear();
		shuffle();
	}
	
	public FortuneCard draw() {
		if (deck.size() == 0) refresh();
		return deck.pop();
	}
	
	public Boolean discard(FortuneCard c) {
		if (c == null) return false;
		discardPile.add(c);
		return true;
	}
}
