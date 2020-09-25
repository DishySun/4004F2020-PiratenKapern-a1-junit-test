package entity;

public class Player {
	private String name;
	private int score;
	
	public Player(String name) {
		this.name = name;
		this.score = 0;
	}
	
	public String getName() {return this.name;}
	public int getScore() {return this.score;}
	public void scoreChange(int delta) {this.score += delta;}
}
