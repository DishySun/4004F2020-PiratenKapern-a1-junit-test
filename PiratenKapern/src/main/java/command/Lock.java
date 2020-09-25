package command;

import java.util.HashSet;

import game.ChestException;
import game.Turn;

public class Lock implements Command {
	private HashSet<Integer> index;
	
	public Lock(HashSet<Integer> i) {
		this.index = i;
	}

	@Override
	public void execute(Turn t) throws ChestException {
		t.lock(index);
	}

}
