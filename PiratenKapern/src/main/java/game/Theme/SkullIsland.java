package game.Theme;

import java.util.HashMap;

import entity.Dice;
import entity.Dice.Face;
import game.OneTurnScoreChange;

public class SkullIsland implements Theme {

	@Override
	public OneTurnScoreChange scoreCalculation(HashMap<Face, Integer> map,int skullsFromCard) {
		System.out.println("Skulls x" + map.get(Dice.Face.SKULL) + "in Skull Island -" + (map.get(Dice.Face.SKULL) * 100) + " for all other players");
		return new OneTurnScoreChange(OneTurnScoreChange.Range.OTHER, map.get(Dice.Face.SKULL) * -100);
	}

}
