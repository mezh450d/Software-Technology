package kickstart.football;


import java.time.LocalDateTime;
import javax.persistence.Entity;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;

@Entity
public class FootballMatch extends Data<Score> {

	private final String home;
	private final String guest;

	@SuppressWarnings({ "unused", "deprecation" })
	public FootballMatch(String name, Money price, LocalDateTime date,
						 String home, String guest) {
		super(name, price, date);

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
		return guest+" @ "+home+" on "+date.toString();
	}

}