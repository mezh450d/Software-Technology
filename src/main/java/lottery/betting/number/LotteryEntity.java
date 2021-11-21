package lottery.betting.number;

import lottery.betting.Category;
import lottery.betting.Data;
import org.javamoney.moneta.Money;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Entity
public class LotteryEntity extends Data {

	private String numStr;
	private String superNumber;

	public LotteryEntity(){super();}

	public LotteryEntity(String name, Money price, LocalDateTime date, Category category, String numStr, String superNumber) {
		super(name, price, date, category);
		this.numStr = numStr;
		this.superNumber = superNumber;
	}


	@Override
	public String toString() {
		return "Lotto " + this.getName() + " am " + getDate();
	}
}
