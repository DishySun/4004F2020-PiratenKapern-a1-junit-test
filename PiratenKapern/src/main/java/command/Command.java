package command;

import game.ChestException;
import game.Game;

public interface Command {
	public void execute(Game g);
}
