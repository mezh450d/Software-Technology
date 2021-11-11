package kickstart.finance;

import com.mysema.commons.lang.Assert;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
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
	public  String note;
	public static Map<Long, FinanceEntry> ALL_AMOUNT=new HashMap<Long, FinanceEntry>();
	public static List<Double> amounts = new ArrayList<>();

	@SuppressWarnings("unused")
	FinanceForm(){
	}

	public FinanceForm( Double amount, String note){
//		Assert.notNull(amount,"Amount must not to be null!");
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

	private LocalDateTime getDate() { return date; }

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
		System.out.println(amountsWithoutSign);
		while (iterator.hasNext() ) {
			ff.setBalance(ff.balance + iterator.next());
		}
//		System.out.println(ff.balance);
		return ff.balance;
	}


	@Override
	public String toString(){
		return note;
	}


	FinanceEntry toNewEntry() {
		return new FinanceEntry( getAmount(), getNote(), getDate() );
	}
	FinanceEntry toNewEntry1() {
		return new FinanceEntry( -(getAmount()), getNote(), getDate() );
	}

}