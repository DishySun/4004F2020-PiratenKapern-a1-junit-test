package game.Theme;

import java.util.HashMap;

import entity.Dice;
import entity.Dice.Face;
import game.OneTurnScoreChange;

public class Normal implements Theme{

	@Override
	public OneTurnScoreChange scoreCalculation(HashMap<Face, Integer> map, int skullsFromCard) {
		if(map.get(Dice.Face.SKULL) > 2) return new OneTurnScoreChange(OneTurnScoreChange.Range.SELF, 0);
		int score = 0;
		for (Dice.Face f : Dice.Face.values()) {
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
					score += 4000;
					break;
				case 9:
					score += 4000;
					break;
				default: break;
			}
			if (f == Dice.Face.COIN || f == Dice.Face.DIAMOND) score += 100 * map.get(f);
		}
		return new OneTurnScoreChange(OneTurnScoreChange.Range.SELF, score);
	}

}
