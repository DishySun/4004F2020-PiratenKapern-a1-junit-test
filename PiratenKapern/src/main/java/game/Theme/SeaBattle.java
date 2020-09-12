package game.Theme;

import java.util.HashMap;

import entity.Dice.Face;
import game.OneTurnScoreChange;

public class SeaBattle implements Theme{
	private int swordRequired;
	private int reward;
	
	public SeaBattle(int require, int reward) {
		this.swordRequired = require;
		this.reward = reward;
	}

	@Override
	public OneTurnScoreChange scoreCalculation(HashMap<Face, Integer> map) {
		// TODO Auto-generated method stub
		return null;
	}

}
