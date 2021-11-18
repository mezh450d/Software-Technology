package kickstart.finance;

import lottery.finance.FinanceEntry;
import org.salespointframework.useraccount.UserAccount;

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
	@NotNull(message = "amount cannot be null.")
	@Min(value = 0)
	public  Double amount;
	@Min(value = 0)
	public  Double balance;
	public  String note;
	public static Map<Long, FinanceEntry> ALL_AMOUNT=new HashMap<Long, FinanceEntry>();
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

	public Double calculateBalance(){
		FinanceEntry ff = FinanceForm.ALL_AMOUNT.get(getId());
		if (ff == null) {
			ff = new FinanceEntry();
			FinanceForm.ALL_AMOUNT.put(getId(), ff);
		}
		ff.setBalance(0.0);
		Iterable<Double> amountsWithoutSign = getAmounts();
		Iterator<Double> iterator = amountsWithoutSign.iterator() ;
		while (iterator.hasNext()) {
			double temp = iterator.next();
			ff.setBalance(ff.balance + temp);
		}
		return ff.balance;
	}

	public void setBalance(Double balance){
		this.balance = balance;
	}

	public Double getBalance(){
		return balance;
	}
	@Override
	public String toString(){
		return note;
	}


}