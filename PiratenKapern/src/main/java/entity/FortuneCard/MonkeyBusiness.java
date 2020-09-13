package entity.FortuneCard;

import game.Turn;

public class MonkeyBusiness implements FortuneCard {

	@Override
	public void effect(Turn turn) {
		turn.monkeyBusiness();
	}
	@Override
	public int getSkullsFromCard() {return 0;}

}
