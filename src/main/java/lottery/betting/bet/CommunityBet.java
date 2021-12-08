package lottery.betting.bet;

import lottery.betting.data.Data;
import lottery.betting.data.Result;
import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.util.Streamable;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Map;

import static org.salespointframework.core.Currencies.EURO;

@Entity
public class CommunityBet extends Bet {

	@ElementCollection
	private final Map<String, Double> amountPerUser = new HashMap<>();

	@SuppressWarnings("unused")
	protected CommunityBet() {}

	public CommunityBet(Data reference, Result value, Type type,
						String community, UserAccount user, Money bettingAmount){

		super(reference, value, type, community);

		addUserToBet(user, bettingAmount);

	}

	public void addUserToBet(UserAccount user, Money amount){
		String username = user.getUsername();
		if(amountPerUser.containsKey(username)){
			Double newAmount = amountPerUser.get(username) + amount.getNumber().doubleValue();
			amountPerUser.replace(username, newAmount);
		} else {
			amountPerUser.put(user.getUsername(), amount.getNumber().doubleValue());
		}
	}

	public Map<String, Double> getAmountPerUser() {
		return amountPerUser;
	}

	public Money getTotalBettingAmount() {
		double total = 0;
		for(Double amount : amountPerUser.values()){
			total += amount;
		}
		return Money.of(total, EURO);
	}

	public Map<String, Double> payOut(){
		Data reference = getReference();
		Map<String, Double> payOut = new HashMap<>();
		if(reference.isSet()){
			evaluate();
			int factor = getValue().compareTo(reference.getResult());
			for(Map.Entry<String, Double> entry: amountPerUser.entrySet()){
				payOut.put(entry.getKey(), factor * entry.getValue());
			}
			return payOut;
		} else {
			return null;
		}
	}
}
