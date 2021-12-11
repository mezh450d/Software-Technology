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
import org.javamoney.moneta.Money;
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

	public void saveIndividualBet(IndividualBet bet){ bets.save(bet); }

	public void saveCommunityBet(CommunityBet bet){
		bets.save(bet);
//		Optional<CommunityBet> existingBet = bets.equalCommunityBet(bet.getOrigin(),
//				bet.getReference(), bet.getValue());

	}

	public Money getMoneyFromAllBets(){
		Streamable<Bet> bets = findNotEvaluatedBets();
		Money amount = Money.of(0, EURO);
		for(Bet bet : bets){
			amount.add(bet.getTotalBettingAmount());
		}
		return amount;
	}

	public Streamable<Data> findDataByCategory(Category category) { return dataCatalog.findByCategory(category); }

	public Data findNextLottery(){
		Streamable<Data> lotteries = findDataByCategory(Category.LOTTERY);
		LocalDateTime now = LocalDateTime.now(Clock.offset(Clock.systemUTC(), Duration.ofHours(1)));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
		for(Data lottery : lotteries){
			if(now.isBefore(LocalDateTime.parse(lottery.getDate(), formatter))){
				return lottery;
			}
		}
		return null;
	}

	public CommunityBet findByCommunityBetId(long id){ return bets.findCommunityBetById(id); }

	public Streamable<Data> findAllData() { return dataCatalog.findAll(); }

	public Streamable<IndividualBet> findBetsByUser(String user) { return bets.findIndividualByUser(user); }

	public Streamable<CommunityBet> findBetsByCommunityAndCategory(String community, Category category) {
		return bets.findCommunityBetsByCommunityAndCategory(community, category);
	}

	public Streamable<Bet> findBetsByData(Data data) { return bets.findByData(data); }

	public Streamable<Bet> findNotEvaluatedBets() { return bets.findNotEvaluatedBets(); }

	public Streamable<Bet> findAllBets() { return bets.findAll(); }
}
