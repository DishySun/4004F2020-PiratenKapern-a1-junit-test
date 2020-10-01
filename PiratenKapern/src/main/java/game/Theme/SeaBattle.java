package game.Theme;

import java.util.HashMap;

import entity.Dice;
import entity.Dice.Face;
import game.OneTurnScoreChange;

public class SeaBattle implements Theme {
	
	private int swordRequired;
	private int reward;
	
	public SeaBattle(int swordRequired, int reward) {
		this.swordRequired = swordRequired;
		this.reward = reward;
	}
	
	public int getRequire() {return this.swordRequired;}
	public int getReward() {return this.reward;}

	@Override
	public OneTurnScoreChange scoreCalculation(HashMap<Face, Integer> map,int skullsFromCard) {
		if(map.get(Dice.Face.SWORD) < swordRequired) return new OneTurnScoreChange(OneTurnScoreChange.Range.SELF, reward * -1);
		int score = reward;
		System.out.println("Won the Sea Battle +" + reward);
		for (Dice.Face f : Dice.Face.values()) {
			if (f == Dice.Face.SKULL) continue;
			switch(map.get(f)) {
				case 3:
					score += 100;
					System.out.println(f+": 3 of a kind +100");
					break;
				case 4:
					score += 200;
					System.out.println(f+": 4 of a kind +200");
					break;
				case 5:
					score += 500;
					System.out.println(f+": 5 of a kind +500");
					break;
				case 6:
					score += 1000;
					System.out.println(f+": 6 of a kind +1000");
					break;
				case 7:
					score += 2000;
					System.out.println(f+": 7 of a kind +2000");
					break;
				case 8:
					score += 4000;
					System.out.println(f+": 8 of a kind +4000");
					break;
				default: break;
			}
		}
		int coinNum = map.get(Dice.Face.COIN);
		if (coinNum > 0) {
			score += 100 * coinNum;
			System.out.println("Coin x"+coinNum+" bouns +"+ (coinNum * 100));
		}
		int diaNum = map.get(Dice.Face.DIAMOND);
		if (diaNum > 0) {
			score += 100 * diaNum;
			System.out.println("Diamond x"+diaNum+" bouns +"+ (diaNum * 100));
		}
		//check for Full Chest
		if (map.get(Dice.Face.SKULL) == skullsFromCard &&
			(map.get(Dice.Face.MONKEY) == 0 || map.get(Dice.Face.MONKEY) > 2) &&
			(map.get(Dice.Face.PARROT) == 0 || map.get(Dice.Face.PARROT) > 2))  {
			System.out.println("Full Chest +500");
			score += 500;
		}
		return new OneTurnScoreChange(OneTurnScoreChange.Range.SELF,score);
	}

}
