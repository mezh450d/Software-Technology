package lottery.betting.football;

import lottery.betting.Result;

public class Score implements Result<Score> {
	private final int home, guest, difference;

	public Score(int home, int guest){
		this.home = home;
		this.guest = guest;
		this.difference = home - guest;
	}

	public int getHome() {
		return home;
	}

	public int getGuest() {
		return guest;
	}

	public int getDifference() {return difference;}

	@Override
	public String toString(){
		return home + " : " + guest;
	}

	@Override
	public int compareTo(Score o) {
		int otherDifference = o.getDifference();
		if(o.getHome() == home && o.getGuest() == guest) return 4;
		if(difference == otherDifference && difference != 0) return 3;
		if((difference > 0 && otherDifference > 0) || (difference == 0 && otherDifference == 0) || (difference < 0 && otherDifference < 0)) return 2;
		else return 0;
	}
}
