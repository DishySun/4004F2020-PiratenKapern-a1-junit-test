package entity.FortuneCard;

import game.Turn;

public class Gold implements FortuneCard {

	@Override
	public void effect(Turn turn) {
		turn.gold();
	}
	@Override
	public int getSkullsFromCard() {return 0;}

	@Override
	public String toString() {return "Gold: You start your turn with one gold coin";}
}
