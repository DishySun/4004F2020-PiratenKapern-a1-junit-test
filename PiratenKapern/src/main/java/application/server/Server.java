package application.server;

import entity.Player;

import java.io.IOException;

import java.net.Socket;
import java.util.HashMap;
import java.net.ServerSocket;

import game.GameControl;

public class Server{
	private HashMap<Player,PlayerSocket> sockets;
	private static int PORT = 4004;
	private GameControl gc;
	private static int PLAYER_NUMBER = 3;
	
	public Server () {
		sockets = new HashMap<Player,PlayerSocket>();
		gc = new GameControl(this);
	}
	
	public void start() throws IOException {
		ServerSocket ss = new ServerSocket(PORT);
		System.out.println("Server Started\nWaiting connection...");
		while (sockets.size() < PLAYER_NUMBER) {
			Socket s = ss.accept();
			PlayerSocket ps = new PlayerSocket(s);
			String name = ps.askName();
			System.out.println("Player " + name + " has connected");
			Player p = gc.addPlayer(name);
			sockets.put(p, ps);
		}
		gc.startGame();
	}
	
	public void announcement(String msg) {
		for (Player p : sockets.keySet()) {
			PlayerSocket pss =sockets.get(p);
			pss.send(msg);
			if (msg.equals("end game")) pss.shutdown();
		}
		
	}

	public void sendTo(String msg, Player p) {
		PlayerSocket ps = sockets.get(p);
		ps.send(msg);
	}

	public String getCommand(String string, Player p) throws IOException {
		PlayerSocket ps = sockets.get(p);
		return ps.getCommand(string);
	}

	public static void main(String args[]) throws IOException {
		Server server = new Server();
		server.start();
	}



}
