package game;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import entity.Player;
import entity.FortuneCard.Deck;
import entity.FortuneCard.FortuneCard;

public class Game {	
	private ArrayList<Player> players;
	private Deck deck;
	private int currentPlayer;
	private GameControl gc;
	private final int END_GAME_SCORE = 6000;
	
	public Game(ArrayList<Player> players, GameControl gc) {
		this.players = players;
		this.gc = gc;
		deck = new Deck();
		currentPlayer = 0;
	}
	
	public Player reportWinner() {
		this.gameStart();
		Player winner = players.get(0);
		int max = players.get(0).getScore();
		for (int i = 1; i < players.size(); i++) {
			if (players.get(i).getScore() > max) {
				winner = players.get(i);
				max = winner.getScore();
			}
		}
		return winner;
	}
	
	private void gameStart() {
		String yousoro;
		try {
			yousoro = "*   　  "+new String("全速前進ヨーソロー！".getBytes(),"UTF-8")+"     *\n";
		} catch (UnsupportedEncodingException e) {
			yousoro = "";
		}
		gc.announcement("******************************\n"
				+ "*         GAME START         *\n"
				+ "*      Full Sail Ahead       *\n"
				+ yousoro
				+ "******************************\n\n");
		while(true) {
			turn();
			if(players.get(currentPlayer).getScore() >= END_GAME_SCORE) {
				currentPlayer = (currentPlayer + 1) % players.size();
				turn();
				currentPlayer = (currentPlayer + 1) % players.size();
				turn();
				for (Player p : players) {
					if (p.getScore() >= END_GAME_SCORE) break;
				}
			}
			currentPlayer = (currentPlayer + 1) % players.size();
		}
	}
	
	

	private void scoreChange(OneTurnScoreChange delta) {
		if (delta.getRange() == OneTurnScoreChange.Range.SELF) 
			players.get(currentPlayer).scoreChange(delta.getChange());
		else {
			for (int i = 0; i < players.size();i++) {
				if (i == currentPlayer) continue;
				players.get(i).scoreChange(delta.getChange());
			}
		}
	}
	
	private void turn() {
		gc.sendToCurrentPlayer("===== Your Turn =====", currentPlayer);
		gc.sendToOtherPlayer("===== "+ players.get(currentPlayer).getName()+ "'s Turn =====", currentPlayer);
		Turn t = new Turn();
		//sleep
		FortuneCard card = deck.draw();
		t.setCard(card);
		//sleep
		if (t.firstRoll()) {
			t.endTurn();
		}else {
			//main phase
			while (t.getDelta() == null) {
				try {
					gc.getCommand(currentPlayer).execute(t);
				} catch (ChestException e) {
					gc.sendToCurrentPlayer(e.getMsg(), currentPlayer);
				};
			}
		}
		this.scoreChange(t.getDelta()); 
	}
	
}
