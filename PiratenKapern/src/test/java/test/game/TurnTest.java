package test.game;

import static org.junit.Assert.*;

import org.junit.Test;

import entity.Dice;
import entity.FortuneCard.*;
import game.Turn;

public class TurnTest {

	@Test
	public void test_Constructor() {
		//initialize a turn, all attributes should be set to default
		Turn t = new Turn();
		assertTrue(t.getscoreMultiplier() == 1); 		//scoreMultiplier = 1
		assertTrue(t.getskullRerollCount() == 0); 		//skullRerollCounter = 0
		assertTrue(t.getTreasureInHand().size() == 6); 	//6 faces
		for (Dice.Face f : Dice.Face.values()) {
			//every face should be counted as 0 at the beginning of the turn
			assertTrue(t.getTreasureInHand().get(f) == 0);
		}
		assertTrue(t.getFortuneCard() == null); //Fortune Card should be set now
		assertTrue(t.getTheme() instanceof game.Theme.Normal);
		assertTrue(t.getHand().size() == 8);  //8 Dice
		for(Dice d : t.getHand()) {
			// Face = SKULL, unlocked at initialization
			assertTrue(d.getFace() == Dice.Face.SKULL);
			assertFalse(d.isLock());
		}
		assertTrue(t.getChest() == null); //chest cannot be accessed unless get Treasure Chest fortune card
	}
	
	@Test
	public void test_SetFortuneCard_TreasureChest() {
		Turn t;
		//test for treasure chest fortune card
		t = new Turn();
		FortuneCard card = new TreasureChest();
		t.setCard(card);
		assertTrue(t.getChest().size() == 0); 			//get access to chest
		assertTrue(t.getFortuneCard() instanceof TreasureChest);
			//others should not change
		assertTrue(t.getscoreMultiplier() == 1); 		//scoreMultiplier = 1
		assertTrue(t.getskullRerollCount() == 0); 		//skullRerollCounter = 0
		assertTrue(t.getTreasureInHand().size() == 6); 	//6 faces
		for (Dice.Face f : Dice.Face.values()) {
			//every face should be counted as 0 at the beginning of the turn
			assertTrue(t.getTreasureInHand().get(f) == 0);
		} 
		assertTrue(t.getTheme() instanceof game.Theme.Normal);
		assertTrue(t.getHand().size() == 8);  //8 Dice
		for(Dice d : t.getHand()) {
			// Face = SKULL, unlocked at initialization
			assertTrue(d.getFace() == Dice.Face.SKULL);
			assertFalse(d.isLock());
		}
	}
	
	@Test
	public void test_SetFortuneCard_Captain() {
		Turn t;
		//test for treasure chest fortune card
		t = new Turn();
		FortuneCard card = new Captain();
		t.setCard(card);
		assertTrue(t.getscoreMultiplier() == 2);		//scoreMultiplier = 2
		assertTrue(t.getFortuneCard() instanceof Captain);
			//others should not change
		assertTrue(t.getskullRerollCount() == 0); 		//skullRerollCounter = 0
		assertTrue(t.getTreasureInHand().size() == 6); 	//6 faces
		for (Dice.Face f : Dice.Face.values()) {
			//every face should be counted as 0 at the beginning of the turn
			assertTrue(t.getTreasureInHand().get(f) == 0);
		}
		assertTrue(t.getTheme() instanceof game.Theme.Normal);
		assertTrue(t.getHand().size() == 8);  //8 Dice
		for(Dice d : t.getHand()) {
			// Face = SKULL, unlocked at initialization
			assertTrue(d.getFace() == Dice.Face.SKULL);
			assertFalse(d.isLock());
		}
		assertTrue(t.getChest() == null); 
	}
	
	@Test
	public void test_SetFortuneCard_Sorceress() {
		Turn t;
		//test for treasure chest fortune card
		t = new Turn();
		FortuneCard card = new Sorceress();
		t.setCard(card);
		assertTrue(t.getskullRerollCount() == 1);
		assertTrue(t.getFortuneCard() instanceof Sorceress); //skullRerollCounter = 0
			//others should not change
		assertTrue(t.getscoreMultiplier() == 1); 		//scoreMultiplier = 1
		 		
		assertTrue(t.getTreasureInHand().size() == 6); 	//6 faces
		for (Dice.Face f : Dice.Face.values()) {
			//every face should be counted as 0 at the beginning of the turn
			assertTrue(t.getTreasureInHand().get(f) == 0);
		} 
		assertTrue(t.getTheme() instanceof game.Theme.Normal);
		assertTrue(t.getHand().size() == 8);  //8 Dice
		for(Dice d : t.getHand()) {
			// Face = SKULL, unlocked at initialization
			assertTrue(d.getFace() == Dice.Face.SKULL);
			assertFalse(d.isLock());
		}
		assertTrue(t.getChest() == null);
	}
	
	@Test
	public void test_SetFortuneCard_SeaBattle() {
		Turn t;
		//test for treasure chest fortune card
		t = new Turn();
		FortuneCard card = new SeaBattle(2,300);
		t.setCard(card);
		assertTrue(t.getTheme() instanceof game.Theme.SeaBattle);
		assertTrue(t.getFortuneCard() instanceof SeaBattle);
			//others should not change
		assertTrue(t.getscoreMultiplier() == 1); 		//scoreMultiplier = 1
		assertTrue(t.getskullRerollCount() == 0);		//skullRerollCounter = 0
		assertTrue(t.getTreasureInHand().size() == 6); 	//6 faces
		for (Dice.Face f : Dice.Face.values()) {
			//every face should be counted as 0 at the beginning of the turn
			assertTrue(t.getTreasureInHand().get(f) == 0);
		} 
		assertTrue(t.getHand().size() == 8);  //8 Dice
		for(Dice d : t.getHand()) {
			// Face = SKULL, unlocked at initialization
			assertTrue(d.getFace() == Dice.Face.SKULL);
			assertFalse(d.isLock());
		}
		assertTrue(t.getChest() == null);
	}
	
	@Test
	public void test_SetFortuneCard_Gold() {
		Turn t;
		//test for treasure chest fortune card
		t = new Turn();
		FortuneCard card = new Gold();
		t.setCard(card);
		for (Dice.Face f : Dice.Face.values()) {
			if (f == Dice.Face.COIN) assertTrue(t.getTreasureInHand().get(f) == 1);
			else assertTrue(t.getTreasureInHand().get(f) == 0);
		} 
		assertTrue(t.getFortuneCard() instanceof Gold);
			//others should not change
		assertTrue(t.getscoreMultiplier() == 1); 		//scoreMultiplier = 1
		assertTrue(t.getskullRerollCount() == 0);		//skullRerollCounter = 0
		assertTrue(t.getTreasureInHand().size() == 6); 	//6 faces
		assertTrue(t.getTheme() instanceof game.Theme.Normal);
		assertTrue(t.getHand().size() == 8);  //8 Dice
		for(Dice d : t.getHand()) {
			// Face = SKULL, unlocked at initialization
			assertTrue(d.getFace() == Dice.Face.SKULL);
			assertFalse(d.isLock());
		}
		assertTrue(t.getChest() == null);
	}
	
	@Test
	public void test_SetFortuneCard_Diamond() {
		Turn t;
		//test for treasure chest fortune card
		t = new Turn();
		FortuneCard card = new Diamond();
		t.setCard(card);
		for (Dice.Face f : Dice.Face.values()) {
			if (f == Dice.Face.DIAMOND) assertTrue(t.getTreasureInHand().get(f) == 1);
			else assertTrue(t.getTreasureInHand().get(f) == 0);
		} 
		assertTrue(t.getFortuneCard() instanceof Diamond);
			//others should not change
		assertTrue(t.getscoreMultiplier() == 1); 		//scoreMultiplier = 1
		assertTrue(t.getskullRerollCount() == 0);		//skullRerollCounter = 0
		assertTrue(t.getTreasureInHand().size() == 6); 	//6 faces
		assertTrue(t.getTheme() instanceof game.Theme.Normal);
		assertTrue(t.getHand().size() == 8);  //8 Dice
		for(Dice d : t.getHand()) {
			// Face = SKULL, unlocked at initialization
			assertTrue(d.getFace() == Dice.Face.SKULL);
			assertFalse(d.isLock());
		}
		assertTrue(t.getChest() == null);
	}
	
	@Test
	public void test_SetFortuneCard_MonkeyBusiness() {
		Turn t;
		//test for treasure chest fortune card
		t = new Turn();
		FortuneCard card = new MonkeyBusiness();
		t.setCard(card);
		assertTrue(t.getTheme() instanceof game.Theme.MonkeyBusiness);
		assertTrue(t.getFortuneCard() instanceof MonkeyBusiness);
			//others should not change
		assertTrue(t.getscoreMultiplier() == 1); 		//scoreMultiplier = 1
		assertTrue(t.getskullRerollCount() == 0);		//skullRerollCounter = 0
		assertTrue(t.getTreasureInHand().size() == 6); 	//6 faces
		for (Dice.Face f : Dice.Face.values()) {
			//every face should be counted as 0 at the beginning of the turn
			assertTrue(t.getTreasureInHand().get(f) == 0);
		} 
		assertTrue(t.getHand().size() == 8);  //8 Dice
		for(Dice d : t.getHand()) {
			// Face = SKULL, unlocked at initialization
			assertTrue(d.getFace() == Dice.Face.SKULL);
			assertFalse(d.isLock());
		}
		assertTrue(t.getChest() == null);
	}
	
	@Test
	public void test_SetFortuneCard_Skulls() {
		Turn t;
		//test for treasure chest fortune card
		t = new Turn();
		FortuneCard card = new Skull(1);
		t.setCard(card);
		for (Dice.Face f : Dice.Face.values()) {
			if (f == Dice.Face.SKULL) assertTrue(t.getTreasureInHand().get(f) == 1);
			else assertTrue(t.getTreasureInHand().get(f) == 0);
		} 
		assertTrue(t.getFortuneCard() instanceof Skull);
			//others should not change
		assertTrue(t.getscoreMultiplier() == 1); 		//scoreMultiplier = 1
		assertTrue(t.getskullRerollCount() == 0);		//skullRerollCounter = 0
		assertTrue(t.getTreasureInHand().size() == 6); 	//6 faces
		assertTrue(t.getTheme() instanceof game.Theme.Normal);
		assertTrue(t.getHand().size() == 8);  //8 Dice
		for(Dice d : t.getHand()) {
			// Face = SKULL, unlocked at initialization
			assertTrue(d.getFace() == Dice.Face.SKULL);
			assertFalse(d.isLock());
		}
		assertTrue(t.getChest() == null);
	}
}
