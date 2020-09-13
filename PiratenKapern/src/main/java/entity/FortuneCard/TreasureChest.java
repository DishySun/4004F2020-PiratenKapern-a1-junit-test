package entity.FortuneCard;

import game.Turn;

public class TreasureChest implements FortuneCard {

	@Override
	public void effect(Turn turn) {
		turn.enableChest();
	}
	
	@Override
	public int getSkullsFromCard() {return 0;}

}
