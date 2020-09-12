package test.game.Theme;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import entity.Dice;
import game.OneTurnScoreChange;
import game.Theme.SkullIsland;

public class SkullIslandTest {

	@Test
	public void test() {
		SkullIsland s = new SkullIsland();
		HashMap<Dice.Face, Integer> map = new HashMap<Dice.Face, Integer>();
		int[][] counts = {
				//[0-5]:dice number [6]expect score [7]skulls from card
				{1,0,3,0,0,4,-100,0},
				{2,0,3,0,3,0,-200,0},
				{3,3,0,2,0,2,-300,2},
				{4,2,2,0,0,0,-400,0},
				{5,0,2,0,2,0,-500,1},
				{6,0,0,0,3,0,-600,1},
				{7,0,0,0,1,0,-700,0},
				{8,0,0,0,0,0,-800,0},
				{9,0,0,0,0,0,-900,1},
				{10,0,0,0,0,0,-1000,2}};
		for(int i = 0; i < 10; i++) {
			for (int j = 0; j < 6; j++) {
				map.put(Dice.Face.values()[j], counts[i][j]);
			}
			System.out.print("["+i+"]");
			assertTrue(s.scoreCalculation(map,counts[i][7]).getChange() == counts[i][6]);
			assertTrue(s.scoreCalculation(map, counts[i][7]).getRange() == OneTurnScoreChange.Range.OTHER);
		}
	}

}
