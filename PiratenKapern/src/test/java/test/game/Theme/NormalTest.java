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
		System.out.print("test 1: ");
		Normal n = new Normal();
		HashMap<Dice.Face, Integer> map = new HashMap<Dice.Face, Integer>();
		int[][] counts = {
			//[0-5]:dice number [6]expect score [7]skulls from card
			{3,1,1,1,1,1,0,0},
			{4,0,0,0,2,2,0,0},
			{2,3,2,1,0,0,100,0},
			{2,4,1,1,0,0,200,0},
			{2,5,1,0,0,0,500,0},
			{2,6,0,0,0,0,1000,0},
			{1,7,0,0,0,0,2000,0},
			{0,8,0,0,0,0,4500,0}};
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 6; j++) {
				map.put(Dice.Face.values()[j], counts[i][j]);
			}
			System.out.print("["+i+"]");
			assertTrue(n.scoreCalculation(map,counts[i][7]).getChange() == counts[i][6]);
		}
	}
	
	@Test
	public void test2() {
		//with coin/diamond bonus (row 11-20)
		System.out.print("\ntest 2: ");
		Normal n = new Normal();
		HashMap<Dice.Face, Integer> map = new HashMap<Dice.Face, Integer>();
		int[][] counts = {
			//[0-5]:dice number [6]expect score [7]skulls from card
			{1,3,1,1,1,1,300,0},
			{0,4,0,0,2,2,1100,0},
			{0,5,0,1,2,0,700,0},
			{0,6,0,1,1,0,1100,0},
			{0,7,0,0,0,1,2600,0},
			{0,8,0,0,0,1,4600,0},
			{2,3,3,0,0,0,200,0},
			{1,3,4,0,0,0,300,0},
			{0,4,4,0,0,0,900,0}};
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 6; j++) {
				map.put(Dice.Face.values()[j], counts[i][j]);
			}
			System.out.print("["+i+"]");
			assertTrue(n.scoreCalculation(map,counts[i][7]).getChange() == counts[i][6]);
		}
	}

	@Test
	public void test3() {
		//coin/diamond oak + bonus (row 21-32)
		System.out.print("\ntest 3: ");
		Normal n = new Normal();
		HashMap<Dice.Face, Integer> map = new HashMap<Dice.Face, Integer>();
		int[][] counts = {
			//[0-5]:dice number [6]expect score [7]skulls from card
			{1,2,2,0,0,3,400,0},
			{1,2,1,0,0,4,600,0},
			{1,1,1,0,0,5,1000,0},
			{1,1,0,0,0,6,1600,0},
			{0,1,0,0,0,7,2700,0},
			{0,0,0,0,0,8,5300,0},
			{0,0,0,0,0,9,5400,0},
			{0,0,0,2,3,3,800,0},
			{0,0,0,1,4,3,1000,0},
			{0,0,0,0,4,4,1700,0},
			{0,0,0,0,3,5,1900,0}};
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 6; j++) {
				map.put(Dice.Face.values()[j], counts[i][j]);
			}
			System.out.print("["+i+"]");
			assertTrue(n.scoreCalculation(map,counts[i][7]).getChange() == counts[i][6]);
		}
	}
	
	@Test
	public void test4() {
		//skulls from card(row34-38)
		System.out.print("\ntest 4: ");
		Normal n = new Normal();
		HashMap<Dice.Face, Integer> map = new HashMap<Dice.Face, Integer>();
		int[][] counts = {
			//[0-5]:dice number [6]expect score [7]skulls from card
			{1,8,0,0,0,0,4500,1},
			{3,7,0,0,0,0,2000,2},
			{1,5,0,0,2,1,1300,1},
			{2,6,1,1,0,0,1000,2}};
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 6; j++) {
				map.put(Dice.Face.values()[j], counts[i][j]);
			}
			System.out.print("["+i+"]");
			assertTrue(n.scoreCalculation(map,counts[i][7]).getChange() == counts[i][6]);
		}
	}
}
