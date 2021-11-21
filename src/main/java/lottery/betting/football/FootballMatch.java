package lottery.betting.football;

import lottery.betting.Category;
import lottery.betting.Data;
import org.javamoney.moneta.Money;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class FootballMatch extends Data {

	private String home, guest;

	protected FootballMatch() { super(); }

	public FootballMatch(String name, Money price, LocalDateTime date, Category category, String home, String guest) {

		super(name, price, date, category);

		this.home = home;
		this.guest = guest;
	}

	public String getHome() {
		return home;
	}

	public String getGuest() {
		return guest;
	}

	@Override
	public String toString(){
		return guest+" @ "+home+" am "+ getDateWithTime();
	}

}
