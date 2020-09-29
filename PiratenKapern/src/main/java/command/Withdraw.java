package command;

import java.util.HashSet;

import game.Game;

public class Withdraw implements Command {
	private HashSet<Integer> index;
	
	public Withdraw(HashSet<Integer> i) {
		this.index = i;
	}

	@Override
	public void execute(Game g) {
		g.withdraw(index);
	}

}
