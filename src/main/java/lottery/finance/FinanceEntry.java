package lottery.finance;


import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.UserAccount;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.*;


@Entity
public class FinanceEntry {

	private String user;
	public  @Id @GeneratedValue long id;
	public  Double amount;
	@Min(value = 0)
	public  Money balance;
	public  String note;
	private LocalDateTime date;
	public static List<Double> amounts = new ArrayList<>();

	@SuppressWarnings("unused")
	public FinanceEntry() {

	}

	public FinanceEntry(UserAccount user, Double amount, String note, LocalDateTime date, Money balance){
		this.user = user.getUsername();
		this.amount = amount;
		this.balance = balance;
		this.note = note;
		this.date = date;
	}

	public long getId(){
		return  id;
	}

	public Double getAmount(){
		return amount;
	}

	public String getUser(){
		return user;
	}

	public String getNote(){
		return note;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setBalance(Money balance){
		this.balance = balance;
	}

	public Money getBalance(){
		return balance;
	}

	public void addAmount(Double amount) {
		amounts.add(amount);
	}

	public static Iterable<Double> getAmounts() {
		return amounts;
	}

	@Override
	public String toString(){
		return note;
	}

}
