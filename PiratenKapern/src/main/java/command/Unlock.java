package command;

import java.util.HashSet;

import game.Game;

public class Unlock implements Command {
	private HashSet<Integer> index;
	
	public Unlock(HashSet<Integer> i) {
		this.index = i;
	}

	@Override
	public void execute(Game g) {
		g.unlock(index);
	}

}
