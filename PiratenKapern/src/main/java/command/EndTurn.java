package command;

import game.Game;

public class EndTurn implements Command {

	@Override
	public void execute(Game g) {
		g.endTurn();
	}

}
