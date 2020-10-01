package entity;

public class Player {
	private String name;
	private int score;
	
	public Player(String name) {
		if (name == "") this.name = "John";
		else this.name = name;
		this.score = 0;
	}
	
	public void setName(String name) {this.name = name;}
	public String getName() {return this.name;}
	public int getScore() {return this.score;}
	public void scoreChange(int delta) {
		this.score += delta;
		if (this.score < 0) this.score = 0;
	}
}
