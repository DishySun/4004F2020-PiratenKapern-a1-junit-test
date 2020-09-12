package entity;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.HashSet;

public class DiceTest {

	@Test
	public void test_Constructor() {
		Dice d = new Dice();
		
		//test for constructor
		assertNotNull(d);
		assertTrue(d.getFace() == Dice.Face.SKULL);	//initialized to skull as default
		assertFalse(d.isLock()); //initialized to unlocked (false) as default
	}
	
	@Test
	public void test_Lock() {
		//test for lock() and unlock() method
		Dice d1 = new Dice();
		assertFalse(d1.isLock());
		d1.lock();
		assertTrue(d1.isLock());
		d1.unlock();
		assertFalse(d1.isLock());
		d1.lock();
		assertTrue(d1.isLock());
	}
	
	@Test
	public void test_Roll() {
		//test for roll() method
		HashSet<Dice.Face> set = new HashSet<Dice.Face>();
		Dice d2 = new Dice();
		set.add(d2.getFace());
		//involved with random number generator with a bound of 6
		//600 test cases should include them all
		for (int i = 0; i < 600; i++) {
			d2.roll();
			set.add(d2.getFace());
			d2.unlock();// to prevent from got locked by rolling a skull
		}
		assertTrue(set.size() == 6);
		for (Dice.Face f : Dice.Face.values()) {
			assertTrue(set.contains(f));
		}
		
		//dice will be locked after getting a skull;
		Dice d = new Dice();
		while(true) {
			d.roll();
			if (d.getFace() == Dice.Face.SKULL) {
				assertTrue(d.isLock());
				break;
			} 
		}
		
		//locked dice should NOT change its face
		Dice.Face face = d2.getFace();
		for (int i = 0; i < 100; i++) {
			d2.lock();
			d2.roll();
			assertTrue(d2.getFace() == face);
		}
	}
}
