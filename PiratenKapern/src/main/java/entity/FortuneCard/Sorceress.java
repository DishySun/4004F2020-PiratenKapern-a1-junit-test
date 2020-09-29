package entity.FortuneCard;

import game.Turn;

public class Sorceress implements FortuneCard {

	@Override
	public void effect(Turn turn) {
		turn.sorceress();
	}
	
	@Override
	public int getSkullsFromCard() {return 0;}

	@Override
	public String toString() {return "Sorceress: You can reroll a skull this turn. (use \"unlock\" command on a locked skull)";}
}
