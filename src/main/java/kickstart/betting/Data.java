package kickstart.football;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;


public abstract class Data<R extends Result> extends Product {

	protected LocalDateTime date;
	protected R result;

	@SuppressWarnings({ "unused", "deprecation" })
	private Data() {}

	public Data(String name, Money price, LocalDateTime date) {

		super(name, price);

		this.date = date;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public R getResult() {
		return result;
	}

	public void setResult(R result) {
		this.result = result;
	}
}
