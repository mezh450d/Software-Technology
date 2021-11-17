package kickstart.football;

public class Score {
	private final int home, guest;

	public Score(int home, int guest){
		this.home = home;
		this.guest = guest;
	}

	public int getHome() {
		return home;
	}

	public int getGuest() {
		return guest;
	}

	public boolean compareScore(Score score){
		return score.getHome() == home && score.getGuest() == guest;
	}

}
