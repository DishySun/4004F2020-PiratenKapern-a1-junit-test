package command;

import java.util.HashSet;

import game.ChestException;
import game.Turn;

public class Unlock implements Command {
	private HashSet<Integer> index;
	
	public Unlock(HashSet<Integer> i) {
		this.index = i;
	}

	@Override
	public void execute(Turn t) throws ChestException {
		t.unlock(index);
	}

}
