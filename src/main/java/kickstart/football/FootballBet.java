package kickstart.football;

import kickstart.lottery.user.User;
import org.javamoney.moneta.Money;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.useraccount.UserAccount;

import javax.persistence.*;

@Entity
public class FootballBet {

	private  @Id @GeneratedValue long id;

	private String user;
	private String match;
	private int homeScore;
	private int guestScore;
	private Money returnValue;
	private boolean evaluate = false;


	@SuppressWarnings({ "unused", "deprecation" })
	private FootballBet() {}

	public FootballBet(UserAccount user, FootballMatch match, Score prediction, Money bettingAmount){

		this.user = user.getUsername();
		this.match = match.toString();
		this.homeScore = prediction.getHome();
		this.guestScore = prediction.getGuest();
		this.returnValue = bettingAmount.add(bettingAmount.multiply(3));
	}

	public long getId() {
		return id;
	}

	public String getUser() {
		return user;
	}

	public String getMatchID() {
		return match;
	}

	public Score getScore(){
		return new Score(homeScore, guestScore);
	}

	public Money getReturnValue() {
		return returnValue;
	}

	public boolean isEvaluate() {
		return evaluate;
	}
}

