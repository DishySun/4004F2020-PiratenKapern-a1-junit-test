package command;

import java.util.HashSet;

import game.ChestException;
import game.Turn;

public class Stash implements Command {
	private HashSet<Integer> index;
	
	public Stash(HashSet<Integer> i) {
		this.index = i;
	}

	@Override
	public void execute(Turn t) throws ChestException {
		t.moveToChest(index);
	}

}
