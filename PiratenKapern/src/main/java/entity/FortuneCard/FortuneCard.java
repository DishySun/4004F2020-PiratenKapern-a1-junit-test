package entity.FortuneCard;

import game.Turn;

public interface FortuneCard {
	public void effect(Turn turn);
	public int getSkullsFromCard();
}
