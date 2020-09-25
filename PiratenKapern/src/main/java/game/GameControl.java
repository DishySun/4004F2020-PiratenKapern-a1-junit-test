package game;

import java.util.ArrayList;

import command.Command;
import entity.Player;

public class GameControl {
	private final int PLAY_NUMBER =3;
	
	private ArrayList<Player> players;
	private Game game;
	
	public GameControl() {
		players = new ArrayList<Player>();
	}
	
	public void addPlayer(String name) {
		players.add(new Player(name));
		announcement(name + " has join the game. Current player number: " + players.size());
		if (players.size() >= PLAY_NUMBER) startGame();
	}
	
	public void startGame() {
		game = new Game(players, this);
		Player winner = game.reportWinner();
		this.announcement("Winner is " + winner.getName());
	}
	
	public void announcement(String msg) {
		//TODO
	}
	
	public void sendToCurrentPlayer(String msg , int currentPlayer) {
		//TODO implement needed
	}
	
	public void sendToOtherPlayer(String msg, int currentPlayer) {
		//TODO
	}
	
	public Command getCommand(int currentPlayer) {
		//TODO
		return null;
	}
	
}
