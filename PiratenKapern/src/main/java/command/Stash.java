package command;

import java.util.HashSet;

import game.Game;

public class Stash implements Command {
	private HashSet<Integer> index;
	
	public Stash(HashSet<Integer> i) {
		this.index = i;
	}

	@Override
	public void execute(Game g) {
		g.stash(index);
	}

}
