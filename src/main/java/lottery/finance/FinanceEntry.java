package lottery.finance;

import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.util.Assert;

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
	public   Double amount;
	@Min(value = 0)
	public Money balance;
	public  String note;
	private LocalDateTime date;
	public static Map<Long, FinanceEntry> ALL_AMOUNT=new HashMap<Long, FinanceEntry>();
	public static List<Double> amounts = new ArrayList<>();

	@SuppressWarnings("unused")
	public FinanceEntry() {

	}

	public FinanceEntry(UserAccount user, Double amount, String note, LocalDateTime date){
		Assert.notNull(user, "user must not be null!");
		Assert.notNull(amount, "amount must not be null!");
		this.user = user.getUsername();
		this.amount = amount;
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

	public Money calculateBalance(){
		FinanceEntry ff = FinanceEntry.ALL_AMOUNT.get(getId());
		if (ff == null) {
			ff = new FinanceEntry();
			FinanceEntry.ALL_AMOUNT.put(getId(), ff);
		}
		ff.setBalance(Money.of(0.0,"EUR"));
		Iterable<Double> amountsWithoutSign = getAmounts();
		Iterator<Double> iterator = amountsWithoutSign.iterator() ;
		while (iterator.hasNext()) {
			double temp = iterator.next();
			ff.setBalance(ff.balance.add(Money.of(temp,"EUR")));
		}
		return ff.balance;
	}

	public static Iterable<Double> getAmounts() {
		return amounts;
	}

	@Override
	public String toString(){
		return note;
	}

}
