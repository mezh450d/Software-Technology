package lottery.finance;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;


public class FinanceForm {

	@NotNull(message = "amount cannot be null.")
	@Min(value = 0)
	public Double amount;

	public String note;

	private LocalDateTime date;

	@SuppressWarnings("unused")
	FinanceForm(){}

	public FinanceForm(Double amount, String note){
		this.amount = amount;
		this.date = LocalDateTime.now(Clock.offset(Clock.systemUTC(), Duration.ofHours(1)));
		this.note = note;
	}

	public Double getAmount(){
		return amount;
	}

	public LocalDateTime getDate() { return date; }

	public String getNote(){
		return note;
	}

//	public Money calculateBalance(){
//		FinanceEntry entry = FinanceForm.ALL_AMOUNT.get(getId());
//		if (entry == null) {
//			entry = new FinanceEntry();
//			FinanceForm.ALL_AMOUNT.put(getId(), entry);
//		}
//		entry.setBalance(Money.of(0.0,"EUR"));
//		Iterable<Double> amountsWithoutSign = getAmounts();
//		Iterator<Double> iterator = amountsWithoutSign.iterator() ;
//		while (iterator.hasNext()) {
//			double temp = iterator.next();
//			entry.setBalance(entry.balance.add(Money.of(temp,"EUR")));
//		}
//		return entry.balance;
//	}

}