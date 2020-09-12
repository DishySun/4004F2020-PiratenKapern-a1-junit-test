package test.game.Theme;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.HashMap;
import entity.Dice;
import game.Theme.Normal;

public class NormalTest {

	@Test
	public void test1() {
		//no coin/diamond bonus (row 2-10)
		Normal n = new Normal();
		HashMap<Dice.Face, Integer> map = new HashMap<Dice.Face, Integer>();
		int[][] counts = {{3,1,1,1,1,1,0},
			{4,0,0,0,2,2,0},
			{2,3,2,1,0,0,100},
			{2,4,1,1,0,0,200},
			{2,5,1,0,0,0,500},
			{2,6,0,0,0,0,1000},
			{1,7,0,0,0,0,2000},
			{0,8,0,0,0,0,4000}};
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 6; j++) {
				map.put(Dice.Face.values()[j], counts[i][j]);
			}
			System.out.print(i);
			assertTrue(n.scoreCalculation(map).getChange() == counts[i][6]);
		}
	}
	
	@Test
	public void test2() {
		//with coin/diamond bonus (row 11-20)
		Normal n = new Normal();
		HashMap<Dice.Face, Integer> map = new HashMap<Dice.Face, Integer>();
		int[][] counts = {{1,3,1,1,1,1,300},
			{0,4,0,0,2,2,600},
			{2,3,2,1,0,0,100},
			{0,5,0,1,2,0,700},
			{2,5,1,0,0,0,500},
			{0,6,0,1,1,0,1100},
			{0,7,0,0,0,1,2100},
			{0,8,0,0,0,1,4100},
			{2,3,3,0,0,0,200},
			{1,3,4,0,0,0,300},
			{0,4,4,0,0,0,400}};
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 6; j++) {
				map.put(Dice.Face.values()[j], counts[i][j]);
			}
			System.out.print(i);
			assertTrue(n.scoreCalculation(map).getChange() == counts[i][6]);
		}
	}

	public void test3() {
		//coin/diamond oak + bonus (row 21-32)
		Normal n = new Normal();
		HashMap<Dice.Face, Integer> map = new HashMap<Dice.Face, Integer>();
		int[][] counts = {{1,3,1,1,1,1,300},
			{1,2,2,0,0,3,400},
			{1,2,1,0,0,4,600},
			{1,1,1,0,0,5,1000},
			{1,1,0,0,0,6,1600},
			{0,1,0,0,0,7,2700},
			{0,0,0,0,0,8,4800},
			{0,0,0,0,0,9,4900},
			{0,0,0,2,3,3,800},
			{0,0,0,1,4,3,1000},
			{0,0,0,0,4,4,1200},
			{0,0,0,0,4,5,1400}};
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 6; j++) {
				map.put(Dice.Face.values()[j], counts[i][j]);
			}
			System.out.print(i);
			assertTrue(n.scoreCalculation(map).getChange() == counts[i][6]);
		}
	}
}
