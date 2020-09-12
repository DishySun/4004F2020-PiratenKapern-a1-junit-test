package entity;

public class Player {
	private static int idGen = 1001;
	private int id;
	private String name;
	private int score;
	
	public Player(String name) {
		this.id = idGen++;
		this.name = name;
		this.score = 0;
	}
	
	public String getName() {return this.name;}
	public int getID() {return this.id;}
	public int getScore() {return this.score;}
	public void scoreChange(int delta) {this.score += delta;}
}
