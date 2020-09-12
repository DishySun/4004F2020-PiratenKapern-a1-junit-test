package game.Theme;

import java.util.HashMap;

import entity.Dice;
import game.OneTurnScoreChange;

public interface Theme {
	public OneTurnScoreChange scoreCalculation(HashMap<Dice.Face, Integer> map);
}
