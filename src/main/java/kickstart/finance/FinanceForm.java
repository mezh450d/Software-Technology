package kickstart.finance;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinanceForm {
	public  @Id
	@GeneratedValue
	long id;
	private LocalDateTime date;
	public   Double amount, balance;
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