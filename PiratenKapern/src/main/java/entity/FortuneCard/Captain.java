package entity.FortuneCard;

import game.Turn;

public class Captain implements FortuneCard {

	@Override
	public void effect(Turn turn) {
		turn.captain();
	}

	@Override
	public int getSkullsFromCard() {return 0;}
	
	@Override
	public String toString() {return "Captain: The score you make this turn is doubled!";}

}
