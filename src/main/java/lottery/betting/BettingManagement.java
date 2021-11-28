package lottery.betting;

import lottery.finance.FinanceEntry;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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

	public Iterable<Data> findDataByCategory(Category category) { return dataCatalog.findByCategory(category); }

	public Streamable<Data> findAllData() { return dataCatalog.findAll(); }

	public Streamable<Bet> findBetsByUser(String user) { return bets.findByUser(user); }

	public Streamable<Bet> findNotEvaluatedBets() { return bets.findNotEvaluatedBets(); }

	public Streamable<Bet> findAllBets() { return bets.findAll(); }
}
