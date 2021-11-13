package kickstart.finance;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;



@Entity
public class FinanceEntry {

	public  @Id @GeneratedValue long id;
	public   Double amount;
	@Min(value = 0)
	public   Double balance;
	public  String note;
	private LocalDateTime date;

	@SuppressWarnings("unused")
	FinanceEntry() {
		this.amount = null;
		this.note = null;
		this.date = null;
	}

	public FinanceEntry( Double amount, String note, LocalDateTime date){
//		Assert.notNull(amount,"Amount must not to be null!");
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

	public String getNote(){
		return note;
	}

	public LocalDateTime getDate() {
		return date;
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
