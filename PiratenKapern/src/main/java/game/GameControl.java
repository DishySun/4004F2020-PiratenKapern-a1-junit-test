package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import application.server.Server;
import entity.Dice;
import entity.Player;
import entity.FortuneCard.FortuneCard;

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
		System.out.println("\n*****" + p.getName() + " has won the game!*****\nThe server shuts down\n");
		this.announcement("\n*****" + p.getName() + " has won the game!*****\n");
		this.game.showScore();
		this.announcement("game end");
	}

	public void startGame() {
		String s = "";
		while (!s.equals("y") && !s.equals("n")) {
			System.out.print("Server Controlled Game?(Y/N)");
			s = new Scanner(System.in).nextLine().trim().toLowerCase();
		}
		if (s.equals("y")) this.game = new ServerControlledGame(players,this);
		else this.game = new Game(players, this);
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

	public FortuneCard cheatDrawCard() {
		while(true) {
			System.out.print("What Fortune Card will this player draw: ");
			String fc = new Scanner(System.in).nextLine();
			String arg[] = fc.trim().toLowerCase().split(" ");
			if(arg.length < 1) continue;
			switch (arg[0]) {
			case "ca":
				return new entity.FortuneCard.Captain();
			case "di":
				return new entity.FortuneCard.Diamond();
			case "go":
				return new entity.FortuneCard.Gold();
			case "mb":
				return new entity.FortuneCard.MonkeyBusiness();
			case "so":
				return new entity.FortuneCard.Sorceress();
			case "tc":
				return new entity.FortuneCard.TreasureChest();
			case "sb":
				if (arg.length != 3) {
					System.err.println("Format error: SB <int: sword require> <int: reward>");
					continue;
				}
				try {
					int i = Integer.parseInt(arg[1]);
					if (i > 5) {
						System.err.println("sb: too many swords required.");
						continue;
						}
					int j = Integer.parseInt(arg[2]);
					return new entity.FortuneCard.SeaBattle(i, j);
				}catch(NumberFormatException e) {
					System.err.println("need 2 integers after \"sb\"");
					continue;
				}
			case "sk":
				if (arg.length != 2) {
					System.err.println("Format error: SK <int: skulls>");
					continue;
				}
				try {
					int i = Integer.parseInt(arg[1]);
					return new entity.FortuneCard.Skull(i);
				}catch(NumberFormatException e) {
					System.err.println("need a integer after \"sk\"");
					continue;
				}
			default:
				System.err.println("Invalid Card");
				continue;
			}
		}
	}

	public ArrayList<Dice> cheatGetDice() {
		while (true) {
			System.out.print("What dice will this player roll: ");
			String diceString = new Scanner(System.in).nextLine();
			String arg[] = diceString.trim().toLowerCase().split(" ");
			ArrayList<Dice> dice = new ArrayList<Dice>();
			for (String s : arg) {
				if (!s.equals("sk") && !s.equals("mo") && !s.equals("pa") && !s.equals("sw") & !s.equals("co") && !s.equals("di")) {
					System.err.println("Invalid die: "+ s);
					break;
				}
				dice.add(new Dice(s));
			}
			return dice;
		}
	}
}
