package test.game.Theme;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import entity.Dice;
import game.Theme.SeaBattle;

public class SeaBattleTest {
	@Test
	public void test_2_sabre() {
		SeaBattle s = new SeaBattle(2, 100);
		HashMap<Dice.Face, Integer> map = new HashMap<Dice.Face, Integer>();
		int[][] counts = {
			//[0-5]:dice number [6]expect score [7]skulls from card
				{0,0,0,1,4,3,-100},
				{0,2,1,2,3,0,500},
				{0,0,0,3,3,2,1300}};
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 6; j++) {
				map.put(Dice.Face.values()[j], counts[i][j]);
			}
			System.out.println("["+i+"]except: "+counts[i][6]+ " result: "+s.scoreCalculation(map, 0).getChange());
			assertTrue(s.scoreCalculation(map,0).getChange() == counts[i][6]);
		}
	}
	
	@Test
	public void test_3_sabre() {
		SeaBattle s = new SeaBattle(3, 200);
		HashMap<Dice.Face, Integer> map = new HashMap<Dice.Face, Integer>();
		int[][] counts = {
			//[0-5]:dice number [6]expect score [7]skulls from card
				{0,0,3,2,3,0,-200},
				{0,0,2,3,3,0,400},
				{0,0,0,4,2,2,800}};
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 6; j++) {
				map.put(Dice.Face.values()[j], counts[i][j]);
			}
			System.out.println("["+i+"]except: "+counts[i][6]+ " result: "+s.scoreCalculation(map, 0).getChange());
			assertTrue(s.scoreCalculation(map,0).getChange() == counts[i][6]);
		}
	}
	
	@Test
	public void test_4_sabre() {
		SeaBattle s = new SeaBattle(4, 500);
		HashMap<Dice.Face, Integer> map = new HashMap<Dice.Face, Integer>();
		int[][] counts = {
			//[0-5]:dice number [6]expect score [7]skulls from card
				{0,0,2,3,3,0,-500},
				{2,0,2,4,0,0,700},
				{0,0,0,5,1,2,1800}};
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 6; j++) {
				map.put(Dice.Face.values()[j], counts[i][j]);
			}
			System.out.println("["+i+"]except: "+counts[i][6]+ " result: "+s.scoreCalculation(map, 0).getChange());
			assertTrue(s.scoreCalculation(map,0).getChange() == counts[i][6]);
		}
	}
}
