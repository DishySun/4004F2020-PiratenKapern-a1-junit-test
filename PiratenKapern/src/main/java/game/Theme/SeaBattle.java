package game.Theme;

import java.util.HashMap;

import entity.Dice;
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
		if(map.get(Dice.Face.SWORD) < swordRequired) return new OneTurnScoreChange(OneTurnScoreChange.Range.SELF, reward * -1);
		if (map.get(Dice.Face.SKULL) > 2) return new OneTurnScoreChange(OneTurnScoreChange.Range.SELF,0);
		int score = reward;
		for (Dice.Face f : Dice.Face.values()) {
			//TODO does 3 more skulls counts for score?
			if (f == Dice.Face.SKULL) continue;
			System.out.println("a: "+ f + score);
			switch(map.get(f)) {
				case 3:
					score += 100;
					break;
				case 4:
					score += 200;
					break;
				case 5:
					score += 500;
					break;
				case 6:
					score += 1000;
					break;
				case 7:
					score += 2000;
					break;
				case 8:
					score += 5000;
					break;
				default: break;
			}
			System.out.println("b: "+ f + score);
			if (f == Dice.Face.COIN || f == Dice.Face.DIAMOND) score += 100 * map.get(f);
			System.out.println("c: "+ f + score);
		}
		//check for Full Chest
		if (map.get(Dice.Face.SKULL) == skullsFromCard &&
			(map.get(Dice.Face.MONKEY) == 0 || map.get(Dice.Face.MONKEY) > 2) &&
			(map.get(Dice.Face.PARROT) == 0 || map.get(Dice.Face.PARROT) > 2) &&
			(map.get(Dice.Face.SWORD) == 0 || map.get(Dice.Face.SWORD) > 2))  score += 500;
		return new OneTurnScoreChange(OneTurnScoreChange.Range.SELF,score);
	}

}
