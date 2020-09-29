package command;

import game.Game;

public class Roll implements Command {

	@Override
	public void execute(Game g) {
		g.reroll();
	}

}
