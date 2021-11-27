package lottery.finance;

import lottery.finance.FinanceEntry;
import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;

import javax.persistence.Entity;
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
	@Min(value = 0)
	public  Double amount;
	@Min(value = 0)
	public Money balance;
	public  String note;
	public static Map<Long, FinanceEntry> ALL_AMOUNT=new HashMap<Long, FinanceEntry>();
	public static List<Double> amounts = new ArrayList<>();
	List<Double> personalAmounts = new ArrayList<>();
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

	public Money calculateBalance(List<Double> list) {
		balance = Money.of(0.0,"EUR");
		for (double temp : list) {
			balance = balance.add(Money.of(temp, "EUR"));
		}
		setBalance(balance);
		return balance;
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