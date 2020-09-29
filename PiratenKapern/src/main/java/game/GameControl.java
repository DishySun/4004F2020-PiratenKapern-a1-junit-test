package game;

import java.io.IOException;
import java.util.ArrayList;

import application.server.Server;
import command.Command;
import entity.Player;

public class GameControl {
	
	private ArrayList<Player> players;
	private Server server;
	private Game game;
	
	public GameControl(Server s) {
		this.server = s;
		players = new ArrayList<Player>();
	}
	
	public Player addPlayer(String name) {
		Player p;
		p = new Player(name);
		players.add(p);
		return p;
	}
	
	
	public void announcement(String msg) {
		server.announcement(msg);
	}
	
	public void sendToCurrentPlayer(String msg) {
		Player p = players.get(game.getCurrentPlayer());
		server.sendTo(msg, p);
	}
	
	public void sendToOtherPlayer(String msg) {
		Player curP = players.get(game.getCurrentPlayer());
		for (Player p : players) {
			if (p == curP) continue;
			server.sendTo(msg,p);
		}
	}
	
	public void announceWinner(Player p) {
		// TODO Auto-generated method stub
		
	}

	public void startGame() {
		this.game = new Game(players, this);
		this.game.gameStart();
	}

	public void getCommand(String string) {
		Player p = players.get(game.getCurrentPlayer());
		try {
			String commandString = server.getCommand(string, p);
			game.getCommandFactory().creatCommand(commandString);
		} catch (IOException e) {
			game.getCommandFactory().creatCommand("end");
		}
		
	}
}
