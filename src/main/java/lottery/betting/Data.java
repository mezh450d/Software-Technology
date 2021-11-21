package lottery.betting;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public abstract class Data extends Product {

	private LocalDateTime date;

	private Category category;

	private Result result;

	private Boolean set = false;

	@SuppressWarnings({ "unused", "deprecation" })
	protected Data() {}

	public Data(String id, Money price, LocalDateTime date, Category category) {

		super(id, price);

		this.date = date;
		this.category = category;
	}

	public String getDate() {
		return date.getDayOfMonth() + "." + date.getMonthValue() + "." + date.getYear();
	}

	public String getDateWithTime() {
		return date.getDayOfMonth() + "." + date.getMonthValue() + "." + date.getYear()
				+ ", " + date.getHour() + ":" + date.getMinute();
	}

	public Category getCategory() {
		return category;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		if (isSet()) return;
		this.result = result;
		set();
	}

	public Boolean isSet() {
		return set;
	}

	public void set() { set = true; }

	public abstract String toString();
}
