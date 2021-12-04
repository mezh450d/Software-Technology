package lottery.betting;

import lottery.betting.number.LotteryEntity;
import org.apache.tomcat.jni.Local;
import org.javamoney.moneta.Money;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
			amount += bet.getBettingAmount().getNumber().doubleValue();
		}
		return Money.of(amount, EURO);
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

	public Streamable<Data> findAllData() { return dataCatalog.findAll(); }

	public Streamable<Bet> findBetsByUser(String user) { return bets.findByUser(user); }

	public Streamable<Bet> findBetsByData(Data data) { return bets.findByData(data); }

	public Streamable<Bet> findNotEvaluatedBets() { return bets.findNotEvaluatedBets(); }

	public Streamable<Bet> findAllBets() { return bets.findAll(); }
}
