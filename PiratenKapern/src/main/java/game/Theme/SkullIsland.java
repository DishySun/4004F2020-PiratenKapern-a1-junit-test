package game.Theme;

import java.util.HashMap;

import entity.Dice;
import entity.Dice.Face;
import game.OneTurnScoreChange;

public class SkullIsland implements Theme {

	@Override
	public OneTurnScoreChange scoreCalculation(HashMap<Face, Integer> map,int skullsFromCard) {
		return new OneTurnScoreChange(OneTurnScoreChange.Range.OTHER, map.get(Dice.Face.SKULL) * -100);
	}

}
