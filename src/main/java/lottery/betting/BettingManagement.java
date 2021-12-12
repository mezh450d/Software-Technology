package lottery.betting;

import lottery.betting.bet.Bet;
import lottery.betting.bet.BetRepository;
import lottery.betting.bet.CommunityBet;
import lottery.betting.bet.IndividualBet;
import lottery.betting.data.Category;
import lottery.betting.data.Data;
import lottery.betting.data.DataCatalog;
import lottery.betting.data.Result;
import lottery.community.Community;
import org.apache.tomcat.jni.Local;
import org.javamoney.moneta.Money;
import org.salespointframework.catalog.ProductIdentifier;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.salespointframework.core.Currencies.EURO;

@Service
@Transactional
public class BettingManagement {

	private final DataCatalog dataCatalog;
	private final BetRepository bets;

	public BettingManagement(DataCatalog dataCatalog, BetRepository bets) {

		Assert.notNull(dataCatalog, "dataCatalog must not be null!");
		Assert.notNull(bets, "bets must not be null!");

		this.dataCatalog = dataCatalog;
		this.bets = bets;
	}

	public void saveBet(Bet bet){ bets.save(bet); }

	public Money getMoneyFromAllBets(){
		Streamable<Bet> bets = findNotEvaluatedBets();
		double amount = 0;
		for(Bet bet : bets){
			amount += bet.getTotalBettingAmount().getNumber().doubleValue();
		}
		return Money.of(amount, EURO);
	}

	public int getLotteryAmountForDuration(LocalDateTime date){
		Streamable<Data> lotteries = findDataByCategory(Category.LOTTERY);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
		int amount = 0;
		for(Data lottery : lotteries){
			if(date.isAfter(LocalDateTime.parse(lottery.getDate(), formatter))){
				amount++;
			} else {
				break;
			}
		}
		return amount;
	}

	//DATA-REQUESTS

	public Data findDataById(String id){ return dataCatalog.findById(id); }

	public Streamable<Data> findDataByCategory(Category category) { return dataCatalog.findByCategory(category); }

	public Streamable<Data> findAllData() { return dataCatalog.findAll(); }

	public void deleteData(String dataId){ dataCatalog.deleteById(dataId); }

	//BET-REQUESTS

	public Bet findBetById(long id){ return bets.findBetById(id); }

	public CommunityBet findCommunityBetById(long id){ return bets.findCommunityBetById(id); }

	public Streamable<Bet> findBetsByUser(String user) { return bets.findBetsByOrigin(user); }

	public Streamable<CommunityBet> findBetsByCommunityAndCategory(String community, Category category) {
		return bets.findCommunityBetsByCommunityAndCategory(community, category);
	}

	public Streamable<Bet> findBetsByData(Data data) { return bets.findBetsByData(data); }

	public Streamable<Bet> findNotEvaluatedBets() { return bets.findBetsNotEvaluated(); }

	public Streamable<Bet> findAllBets() { return bets.findAll(); }

	public void deleteBet(Long betId){ bets.deleteById(betId); }
}
