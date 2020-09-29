package command;

import java.util.HashSet;

import game.Game;

public class Lock implements Command {
	private HashSet<Integer> index;
	
	public Lock(HashSet<Integer> i) {
		this.index = i;
	}

	@Override
	public void execute(Game g) {
		g.lock(index);
	}

}
