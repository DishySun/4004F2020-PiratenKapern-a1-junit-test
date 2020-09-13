package entity.FortuneCard;

import game.Turn;
import game.Theme.Theme;

public class SeaBattle implements FortuneCard {
	
	private int swordRequired;
	private int reward;
	
	public SeaBattle(int require, int reward) {
		this.swordRequired = require;
		this.reward = reward;
	}
	
	@Override
	public void effect(Turn turn) {
		turn.seaBattle(new game.Theme.SeaBattle(swordRequired, reward));
	}
	
	@Override
	public int getSkullsFromCard() {return 0;}

}
