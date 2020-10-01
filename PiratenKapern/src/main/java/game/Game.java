package game;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

import command.Command;
import command.CommandFactory;
import entity.Player;
import entity.FortuneCard.Deck;
import entity.FortuneCard.FortuneCard;

public class Game {	
	protected ArrayList<Player> players;
	private Deck deck;
	protected int currentPlayer;
	protected int winnerRound;
	protected Stack<Turn> turns;
	protected ArrayList<Command> commandList;
	protected CommandFactory cf;
	protected GameControl gc;
	protected final int WINNING_SCORE = 6000;
	
	public Game(ArrayList<Player> players, GameControl gc) {
		this.players = players;
		this.gc = gc;
		this.deck = new Deck();
		this.turns = new Stack<Turn>();
		this.commandList = new ArrayList<Command>();
		this.currentPlayer = 0;
		// -1 = all players 6000-
		// 1,2 = turns left is one player hit 6000
		// 0 = real time to decide winner
		this.winnerRound = -1;
	}
	
	public void gameStart() {
		String yousoro;
		try {
			yousoro = "*      "+new String("全速前進ヨーソロー！".getBytes(),"UTF-8")+"            *";
		} catch (UnsupportedEncodingException e) {
			yousoro = "";
		}
		gc.announcement("******************************");
		gc.announcement("*         GAME START         *");
		gc.announcement("*      Full Sail Ahead       *");
		gc.announcement(yousoro);
		gc.announcement("******************************");
		turnStart();
	}
	
	private void turnStart() {
		//sleep
		gc.sendToCurrentPlayer("\n============Your Turn============");
		gc.sendToOtherPlayer("\n============"+players.get(currentPlayer).getName()+"'s Turn============");
		Turn t = new Turn();
		turns.push(t);
		FortuneCard c = deck.draw();
		t.setCard(c);
		gc.sendToCurrentPlayer("\n*You have drawn " + c);
		gc.sendToOtherPlayer("\n*"+players.get(currentPlayer).getName()+" has drawn "+ c);
		firstRoll();
	}
	
	private void firstRoll() {
		//sleep
		Turn t = turns.peek();
		Boolean b = t.firstRoll();
		gc.sendToCurrentPlayer("\n*Your first roll:\n"+t.statString());
		gc.sendToOtherPlayer("\n*"+players.get(currentPlayer).getName() + "'s first roll:\n"+t.statString());
		if (b) {
			gc.sendToCurrentPlayer("You have rolled 3 or more skulls, your turn ends.");
			gc.sendToOtherPlayer(players.get(currentPlayer).getName()+" has rolled 3 or more skulls, turn ends.");
			this.endTurn();
		}else {
			gc.sendToCurrentPlayer("Welcome to Skull Island! Get as much skulls as you can to reduce others' score!");
			gc.sendToOtherPlayer(players.get(currentPlayer) + " have entered Skull Island. You score gonna be... :(");
			this.getCommand();
		}
	}
	
	//player action
	public void stash(HashSet<Integer> index) {
		Turn t = turns.peek();
		try {
			t.moveToChest(index);
		} catch (ChestException e) {
			gc.sendToCurrentPlayer(e.getMsg());
			this.getCommand();
		}
		String s = "";
		for (int i : index) {
			if (i >= 0 && i <t.getHand().size())
				s += "["+i+"] ";
		}
		gc.sendToCurrentPlayer("\n*You have stashed " + s + "to your Treasure Chest.\n"+t.statString());
		gc.sendToOtherPlayer("\n*"+players.get(currentPlayer).getName()+" has stashed "+s+"to Treasure Chest\n" +t.statString());
		this.getCommand();
	}
	
	public void withdraw(HashSet<Integer> index) {
		Turn t = turns.peek();
		try {
			t.moveToHand(index);
		} catch (ChestException e) {
			gc.sendToCurrentPlayer(e.getMsg());
			this.getCommand();
		}
		String s = "";
		for (int i : index) {
			if (i >= 0 && i <t.getHand().size())
				s += "["+i+"] ";
		}
		gc.sendToCurrentPlayer("\n*You have withdrawn " + s + "to your Treasure Chest.\n"+t.statString());
		gc.sendToOtherPlayer("\n*"+players.get(currentPlayer).getName()+" has withdrawn "+s+"to Treasure Chest\n" +t.statString());
		this.getCommand();
	}
	
	public void lock(HashSet<Integer> index) {
		Turn t = turns.peek();
		t.lock(index);
		gc.announcement("\n*Lock:\n"+t.statString());
		this.getCommand();
	}
	
	public void unlock(HashSet<Integer> index) {
		Turn t = turns.peek();
		t.unlock(index);
		gc.announcement("\n*Unlock:\n"+t.statString());
		this.getCommand();
	}
	
	public void reroll() {
		Turn t = turns.peek();
		int flag = t.reroll();
		if (flag == -1) {
			gc.sendToCurrentPlayer("You need to roll at least 2 dice.");
			this.getCommand();
			return;
		}
		gc.sendToCurrentPlayer("\n*You have rerolled:\n"+t.statString());
		gc.sendToOtherPlayer("\n*"+players.get(currentPlayer).getName() + " has rerolled:\n"+t.statString());
		switch(flag) {
		case 0:
			this.getCommand();
			return;
		case 1:
			gc.sendToCurrentPlayer("\n*You have beed disqulified from the turn because you got 3 or more skulls.");
			gc.sendToOtherPlayer("\n*"+players.get(currentPlayer).getName() + " have beed disqulified from the turn because got 3 or more skulls.");
			this.endTurn();
			return;
		case 2:
			gc.sendToCurrentPlayer("\nYou have beend disqulified from the turn because you didn't roll any skulls this reroll in Skull Island.");
			gc.sendToOtherPlayer("\n*"+players.get(currentPlayer).getName()+ " have beend disqulified from the turn because didn't roll any skulls this reroll in Skull Island.");
			this.endTurn();
			return;
		default:
			this.endTurn();
			return;
		}
	}
	public void endTurn() {
		Turn t = turns.peek();
		t.endTurn();
		this.scoreChange(t.getDelta());
		gc.sendToCurrentPlayer("\n^^^^^^^Your Turn Ends^^^^^^^");
		gc.sendToOtherPlayer("\n^^^^^^^"+players.get(currentPlayer).getName()+"'s Turn Ends^^^^^^^");
		this.showScore();
		this.checkWinner();
	}
	
	//end a turn
	public void showScore() {
		gc.announcement("\nScore: ");
		for (Player p: players) {
			gc.announcement("    "+p.getName()+" -- " + p.getScore());
		}
	}
	
	private void checkWinner() {
		if (this.winnerRound == -1) {
			if (players.get(currentPlayer).getScore() >= this.WINNING_SCORE) {
				this.winnerRound = players.size()-2;
				gc.sendToOtherPlayer(players.get(currentPlayer) + " has reach winning point "+this.WINNING_SCORE+"\nUse your next turn to catch him/her, or you lose!");
			}
		}else if (this.winnerRound == 0) {
			int max = 0;
			Player winner = null;
			for (Player p: players) {
				int s = p.getScore();
				if (s > max) {
					max = s;
					winner = p;
				}
			}
			if (max >= this.WINNING_SCORE) this.announceWinner(winner);
			else this.winnerRound = -1;
		}else this.winnerRound--;
		currentPlayer = (currentPlayer + 1) % players.size();
		this.turnStart();
	}
	
	protected void announceWinner(Player p) {
		gc.announceWinner(p);
	}	

	protected void getCommand() {
		cf = new CommandFactory(this);
		Turn t = turns.peek();
		String s = cf.getPrompt(t.getChest() != null);
			gc.getCommand("\n*Captain, it's our time!\n"+ s +"What are we doing next?");
	}
	
	public void reEnterCommand() {
		Turn t = turns.peek();
		String s = cf.getPrompt(t.getChest() != null);
			gc.getCommand("\n*Captain, I can't understand your command.\n"
					+ s + "\nPleaser enter again: ");
		
	}
	
	public void executeCommand(Command c) {
		this.cf = null;
		this.commandList.add(c);
		c.execute(this);
	}
	
	protected void scoreChange(OneTurnScoreChange delta) {
		if (delta.getRange() == OneTurnScoreChange.Range.SELF) {
			players.get(currentPlayer).scoreChange(delta.getChange());
			if (delta.getChange() < 0) {
				gc.sendToCurrentPlayer("\n*You have lost " + delta.getChange()+" score.");
				gc.sendToOtherPlayer("\n*"+players.get(currentPlayer).getName() + " has lost " + delta.getChange()+" score.");
			}else {
				gc.sendToCurrentPlayer("\nYou have gain " + delta.getChange()+" score.");
				gc.sendToOtherPlayer("\n*"+players.get(currentPlayer).getName() + " has gain " + delta.getChange()+" score.");
			}
		}else {
			for (int i = 0; i < players.size();i++) {
				if (i == currentPlayer) continue;
				players.get(i).scoreChange(delta.getChange());
			}
			if (delta.getChange() < 0) {
				gc.sendToCurrentPlayer("Other players have lost "+delta.getChange()+" score.");
				gc.sendToOtherPlayer("You have lost "+delta.getChange()+" score.");
			}else {
				// will not happen;
				gc.sendToCurrentPlayer("Other players have gain "+delta.getChange()+" score.");
				gc.sendToOtherPlayer("You have gain "+ delta.getChange()+" score.");
			}
		}
	}
	
	public CommandFactory getCommandFactory() {return this.cf;}
	public int getCurrentPlayer() {return this.currentPlayer;}
}
