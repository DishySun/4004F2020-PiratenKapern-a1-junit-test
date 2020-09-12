package game;

public class OneTurnScoreChange {
	enum Range{SELF,OTHER}
	private Range range;
	private int delta;
	
	public OneTurnScoreChange(OneTurnScoreChange.Range range, int delta) {
		this.range = range;
		this.delta = delta;
	}
	
	public Range getRange() {return this.range;}
	public int getChange() {return this.delta;}
}
