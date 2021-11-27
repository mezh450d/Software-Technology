package lottery.finance;

import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;



@Entity
public class FinanceEntry {

	private String user;

	@Id @GeneratedValue
	public long id;

	public Double amount;
	public Money balance;
	public String note;
	private LocalDateTime date;

	@SuppressWarnings("unused")
	protected FinanceEntry() {}

	public FinanceEntry(UserAccount user, Double amount, String note, LocalDateTime date, Money balance){
		Assert.notNull(user, "user must not be null!");
		Assert.notNull(amount, "amount must not be null!");
		this.user = user.getUsername();
		this.amount = amount;
		this.note = note;
		this.date = date;
		this.balance = balance;
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

	public String getDate() {
		return date.getDayOfMonth() + "." + date.getMonthValue() + "." + date.getYear();
	}

	public String getDateWithTime() {
		return date.getDayOfMonth() + "." + date.getMonthValue() + "." + date.getYear()
				+ ", " + date.getHour() + ":" + date.getMinute();
	}

	public Money getBalance(){
		return balance;
	}

	public void setBalance(Money balance){
		this.balance = balance;
	}

	@Override
	public String toString(){
		return note;
	}

}