package kickstart.football;

public class Score implements Result{
	private final int home;
	private final int guest;

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

	@Override
	public String toString(){
		return "Score: "+home+" : "+guest;
	}

	public boolean compare(Data<Score> data) {
		Score actualScore = data.getResult();
		return actualScore.getHome() == home && actualScore.getGuest() == getGuest();
	}
}
