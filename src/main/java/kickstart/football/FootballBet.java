package kickstart.football;

import kickstart.lottery.user.User;
import org.javamoney.moneta.Money;
import org.salespointframework.catalog.ProductIdentifier;

import javax.persistence.*;

@Entity
public class FootballBet {

	private  @Id @GeneratedValue long id;

	@OneToOne(cascade = CascadeType.ALL)
	private User user;

	@OneToOne(cascade = CascadeType.ALL)
	private FootballMatch match;

	private int homeScore;
	private int guestScore;
	private Money returnValue;
	private boolean evaluate = false;


	@SuppressWarnings({ "unused", "deprecation" })
	private FootballBet() {}

	public FootballBet(User user, FootballMatch match, Score prediction, Money bettingAmount){

		this.user = user;
		this.match = match;
		this.homeScore = prediction.getHome();
		this.guestScore = prediction.getGuest();
		this.returnValue = bettingAmount.add(bettingAmount.multiply(3));
	}

	public long getId() {
		return id;
	}

	public long getUser() {
		return user.getId();
	}

	public ProductIdentifier getMatchID() {
		return match.getId();
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

