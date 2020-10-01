package game;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import entity.Player;
import entity.FortuneCard.FortuneCard;

public class ServerControlledGame extends Game{
	public ServerControlledGame(ArrayList<Player> players, GameControl gc) {
		super(players, gc);
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
		FortuneCard c = gc.cheatDrawCard();
		t.setCard(c);
		gc.sendToCurrentPlayer("\n*You have drawn " + c);
		gc.sendToOtherPlayer("\n*"+players.get(currentPlayer).getName()+" has drawn "+ c);
		firstRoll();
	}
	
	private void firstRoll() {
		//sleep
		Turn t = turns.peek();
		Boolean b = t.firstRoll(gc.cheatGetDice());
		gc.sendToCurrentPlayer("\n*Your first roll:\n"+t.statString());
		gc.sendToOtherPlayer("\n*"+players.get(currentPlayer).getName() + "'s first roll:\n"+t.statString());
		if (b) {
			gc.sendToCurrentPlayer("You have rolled 3 or more skulls, your turn ends.");
			gc.sendToOtherPlayer(players.get(currentPlayer).getName()+" has rolled 3 or more skulls, turn ends.");
			this.endTurn();
		}else getCommand();
	}
	
	public void reroll() {
		Turn t = turns.peek();
		int flag = t.reroll(gc.cheatGetDice());
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
	
	private void checkWinner() {
		if (this.winnerRound == -1) {
			if (players.get(currentPlayer).getScore() >= this.WINNING_SCORE) this.winnerRound = players.size()-2;
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
			if (max >= this.WINNING_SCORE) {
				this.announceWinner(winner);
				return;
			}
			else this.winnerRound = -1;
		}else this.winnerRound--;
		currentPlayer++;
		this.turnStart();
	}
}
