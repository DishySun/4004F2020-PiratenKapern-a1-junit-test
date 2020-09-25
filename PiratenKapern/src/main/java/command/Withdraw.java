package command;

import java.util.HashSet;

import game.ChestException;
import game.Turn;

public class Withdraw implements Command {
	private HashSet<Integer> index;
	
	public Withdraw(HashSet<Integer> i) {
		this.index = i;
	}

	@Override
	public void execute(Turn t) throws ChestException {
		t.moveToHand(index);
	}

}
