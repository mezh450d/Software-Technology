package lottery.betting.bet;

import lottery.betting.data.Data;
import lottery.betting.data.Result;
import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.UserAccount;

import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Map;

@Entity
public class IndividualBet extends Bet{

	private Money bettingAmount;

	@SuppressWarnings("unused")
	protected IndividualBet() {}

	public IndividualBet(Data reference, Result value, Type type, UserAccount user, Money bettingAmount){

		super(reference, value, type, user);

		this.bettingAmount = bettingAmount;

	}
	public Money getTotalBettingAmount() {
		return bettingAmount;
	}

	public Map<String, Double> payOut(){
		if(getReference().isSet()){
			evaluate();
			int factor = getValue().compareTo(getReference().getResult());
			Map<String, Double> payOut = new HashMap<>();
			payOut.put(getOrigin(), factor * bettingAmount.getNumber().doubleValue());
			return payOut;
		} else {
			return null;
		}
	}

}
