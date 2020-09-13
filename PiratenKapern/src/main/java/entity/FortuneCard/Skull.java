package entity.FortuneCard;

import game.Turn;

public class Skull implements FortuneCard {
	
	private int skullNumber;
	public Skull(int num) {
		this.skullNumber = num;
	}

	@Override
	public void effect(Turn turn) {
		turn.skull(skullNumber);
	}
	
	@Override
	public int getSkullsFromCard() {return skullNumber;}

}
