package entity.FortuneCard;

import game.Turn;

public class Diamond implements FortuneCard {

	@Override
	public void effect(Turn turn) {
		turn.diamond();
	}
	
	@Override
	public int getSkullsFromCard() {return 0;}

	@Override
	public String toString() {return "Diamond: You start your turn with one diamond.";}
}
