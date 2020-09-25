package command;

import game.ChestException;
import game.Turn;

public class EndTurn implements Command {

	@Override
	public void execute(Turn t) throws ChestException {
		t.endTurn();
	}

}
