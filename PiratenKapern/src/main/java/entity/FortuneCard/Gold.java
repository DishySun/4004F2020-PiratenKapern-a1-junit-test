package entity.FortuneCard;

import game.Turn;

public class Gold implements FortuneCard {

	@Override
	public void effect(Turn turn) {
		turn.gold();
	}

}
