package entity.FortuneCard;

import game.Turn;

public class Sorceress implements FortuneCard {

	@Override
	public void effect(Turn turn) {
		turn.sorceress();
	}
	
	@Override
	public int getSkullsFromCard() {return 0;}

}
