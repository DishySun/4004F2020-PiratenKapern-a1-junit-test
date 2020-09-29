package entity.FortuneCard;

import game.Turn;

public class TreasureChest implements FortuneCard {

	@Override
	public void effect(Turn turn) {
		turn.enableChest();
	}
	
	@Override
	public int getSkullsFromCard() {return 0;}

	@Override
	public String toString() {return "Treasure Chest: You can now stash any non-skull dice to your Treasure Chest.\n"
				+"Dice in Treasure Chest will still count for score if you get too many skulls in this turn."
				+ "(You are able to use \"stash\" and \"withdraw\" command this turn)";}
}
