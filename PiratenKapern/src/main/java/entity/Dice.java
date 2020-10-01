package entity;

import java.util.Random;

public class Dice {
	
	public enum Face{
		SKULL,
		MONKEY,
		PARROT,
		SWORD,
		COIN,
		DIAMOND;
	}
	
	private Face face;
	private Boolean lock;
	
	public Dice(){
		this.face = Face.SKULL;
		this.lock = false;
	}
	
	public Dice(String s){
		this.lock = false;
		switch (s){
		case "sk":
			this.face = Face.SKULL;
			this.lock = true;
			break;
		case "mo":
			this.face = Face.MONKEY;
			break;
		case "pa":
			this.face = Face.PARROT;
			break;
		case "sw":
			this.face = Face.SWORD;
			break;
		case "co":
			this.face = Face.COIN;
			break;
		case "di":
			this.face = Face.DIAMOND;
			break;
		}
	}
	
	public Face getFace() {return this.face;}
	public Boolean isLock() {return lock;}
	
	public void roll() {
		if (this.lock) return;
		Random r = new Random();
		this.face = Face.values()[r.nextInt(6)]; 
		if (this.face == Dice.Face.SKULL) this.lock();
	}
	
	public void lock() {this.lock = true;}
	public void unlock() {this.lock = false;}
	
	public String toString() {
		if (this.lock) return "["+this.face.toString()+"]";
		return this.face.toString();
	}
}
