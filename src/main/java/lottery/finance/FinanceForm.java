package lottery.finance;

import org.javamoney.moneta.Money;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;


public class FinanceForm {
	public  @Id
	@GeneratedValue
	long id;
	private LocalDateTime date;
	@NotNull(message = "amount cannot be null.")
	@Min(value = 0)
	public  Double amount;
	@Min(value = 0)
	public Money balance;
	public  String note;
	public static List<Double> amounts = new ArrayList<>();

	@SuppressWarnings("unused")
	FinanceForm(){
	}

	public FinanceForm( Double amount, String note){
		this.amount = amount;
		this.date = LocalDateTime.now();
		this.note = note;
	}

	public long getId(){
		return  id;
	}

	public Double getAmount(){
		return amount;
	}

	public LocalDateTime getDate() { return date; }

	public void addAmount(Double amount) {
		amounts.add(amount);
	}

	public static Iterable<Double> getAmounts() {
		return amounts;
	}

	public String getNote(){
		return note;
	}

	public void setBalance(Money balance){
		this.balance = balance;
	}

	public Money getBalance(){
		return balance;
	}
	@Override
	public String toString(){
		return note;
	}


}