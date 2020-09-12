package game.Theme;

import java.util.HashMap;

import entity.Dice.Face;
import game.OneTurnScoreChange;

public class SeaBattle implements Theme {
	
	private int swordRequired;
	private int reward;
	
	public SeaBattle(int swordRequired, int reward) {
		this.swordRequired = swordRequired;
		this.reward = reward;
	}
	
	public int getRequire() {return this.swordRequired;}
	public int getReward() {return this.reward;}

	@Override
	public OneTurnScoreChange scoreCalculation(HashMap<Face, Integer> map,int skullsFromCard) {
		// TODO Auto-generated method stub
		return null;
	}

}
