package lottery.betting;

import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.UserAccount;

import javax.persistence.*;

@Entity
public class Bet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String user;

	@OneToOne(cascade = CascadeType.ALL)
	private Data reference;

	private Result value;

	private Money bettingAmount;

	private boolean evaluate = false;

	@SuppressWarnings("unused")
	protected Bet() {}

	public Bet(UserAccount user, Data reference, Result value, Money bettingAmount){
		this.user = user.getUsername();
		this.reference = reference;
		this.value = value;
		this.bettingAmount = bettingAmount;
	}

	public long getId() {
		return id;
	}

	public String getUser() {
		return user;
	}

	public Data getReference() { return reference; }

	public Result getValue() { return value; }

	public Money getBettingAmount() {
		return bettingAmount;
	}

	public boolean isEvaluated() {
		return evaluate;
	}

	public void evaluate() {
		evaluate = true;
	}

	public Double payOut(){
		if(reference.isSet()){
			evaluate();
			int factor = value.compareTo(reference.getResult());
			return factor * bettingAmount.getNumber().doubleValue();
		} else {
			return null;
		}
	}
}

