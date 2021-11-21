package lottery.betting.number;

import lottery.betting.Category;
import lottery.betting.Data;
import org.javamoney.moneta.Money;

import javax.persistence.Entity;
import java.time.LocalDateTime;


@Entity
public class LotteryEntity extends Data {

	private String lotteryName;

	public LotteryEntity(){ super(); }

	public LotteryEntity(String name, Money price, LocalDateTime date, Category category, String lotteryName) {

		super(name, price, date, category);

		this.lotteryName = lotteryName;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	@Override
	public String toString() {
		return lotteryName + " am " + getDate();
	}
}