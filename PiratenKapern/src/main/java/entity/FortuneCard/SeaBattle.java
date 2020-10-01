package entity.FortuneCard;

import game.Turn;

public class SeaBattle implements FortuneCard {
	
	private int swordRequired;
	private int reward;
	private final int TWO_BATTLE_REWARD = 300;
	private final int THREE_BATTLE_REWARD = 500;
	private final int FOUR_BATTLE_REWARD = 1000;
	
	public SeaBattle(int require) {
		this.swordRequired = require;
		switch (require) {
		case 2:
			this.reward = TWO_BATTLE_REWARD;
			break;
		case 3:
			this.reward = THREE_BATTLE_REWARD;
			break;
		case 4:
			this.reward = FOUR_BATTLE_REWARD;
			break;
		default:
			System.err.println("Invalid SeeBattle sword");
			System.exit(-1);
		}
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
