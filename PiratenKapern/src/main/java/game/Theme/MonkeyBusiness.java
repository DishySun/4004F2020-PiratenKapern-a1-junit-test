package game.Theme;

import java.util.HashMap;

import entity.Dice;
import entity.Dice.Face;
import game.OneTurnScoreChange;

public class MonkeyBusiness implements Theme {
	
	@Override
	public OneTurnScoreChange scoreCalculation(HashMap<Face, Integer> map,int skullsFromCard) {
		if (map.get(Dice.Face.SKULL) > 2) return new OneTurnScoreChange(OneTurnScoreChange.Range.SELF,0);
		int nakama = 0;
		int score = 0;
		for (Dice.Face f : Dice.Face.values()) {
			if (f == Dice.Face.SKULL) continue;
			if (f == Dice.Face.MONKEY || f == Dice.Face.PARROT) {
				nakama += map.get(f);
				continue;
			}
			switch (map.get(f)) {
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
			if (f == Dice.Face.COIN || f == Dice.Face.DIAMOND) score += 100 * map.get(f);
		}
		//count kinds of nakama
		switch(nakama) {
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
		
		//check for Full Chest
		if (map.get(Dice.Face.SKULL) == skullsFromCard &&
				(map.get(Dice.Face.SWORD) == 0 || map.get(Dice.Face.SWORD) > 2) &&
				(nakama == 0 || nakama > 2))  score += 500;
		return new OneTurnScoreChange(OneTurnScoreChange.Range.SELF, score);
	}

}
