package command;

import game.ChestException;
import game.Turn;

public class Roll implements Command {

	@Override
	public void execute(Turn t) throws ChestException {
		if (t.reroll()) t.endTurn();
	}

}
