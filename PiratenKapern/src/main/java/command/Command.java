package command;

import game.ChestException;
import game.Turn;

public interface Command {
	public void execute(Turn t) throws ChestException;
}
