package entity.FortuneCard;

import game.Turn;

public class MonkeyBusiness implements FortuneCard {

	@Override
	public void effect(Turn turn) {
		turn.monkeyBusiness();
	}
	@Override
	public int getSkullsFromCard() {return 0;}

	@Override
	public String toString() {return "Monkey Bussiness: Monkeys and Parrots counts as a group in this turn.";}
}
