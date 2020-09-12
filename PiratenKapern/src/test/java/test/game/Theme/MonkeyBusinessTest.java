package test.game.Theme;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import entity.Dice;
import game.OneTurnScoreChange;
import game.Theme.MonkeyBusiness;

public class MonkeyBusinessTest {

	@Test
	public void test() {
		MonkeyBusiness m = new MonkeyBusiness();
		HashMap<Dice.Face, Integer> map = new HashMap<Dice.Face, Integer>();
		int[][] counts = {
				//[0-5]:dice number [6]expect score [7]skulls from card
				{2,1,1,2,1,1,200},
				{2,1,2,2,1,0,200},
				{2,2,1,2,1,0,200},
				{2,2,2,2,0,0,200},
				{2,2,3,1,0,0,500},
				{2,3,3,0,0,0,1000},
				{1,1,6,0,0,0,2000},
				{0,3,5,0,0,0,5500},
				{0,2,3,0,1,2,1300}
		};
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 6; j++) {
				map.put(Dice.Face.values()[j], counts[i][j]);
			}
			System.out.println("["+i+"]except: "+counts[i][6]+ " result: "+m.scoreCalculation(map, 0).getChange());
			assertTrue(m.scoreCalculation(map, 0).getChange() == counts[i][6]);
			assertTrue(m.scoreCalculation(map, 0).getRange() == OneTurnScoreChange.Range.SELF);
		}
	}

}
