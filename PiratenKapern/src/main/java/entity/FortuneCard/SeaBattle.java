package entity.FortuneCard;

import game.Turn;

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
	
	@Override
	public String toString() {return "Sea Battle (" + this.swordRequired+", " + this.reward+"): \n"
			+ "Engage to Sea Battle, you cannot enter Island of Skull in this turn."
			+"Collect " + this.swordRequired +" swords to get " + this.reward + " reward on score.\n"
			+"Or lost " + this.reward + " on failure collecting swords.";}

}
