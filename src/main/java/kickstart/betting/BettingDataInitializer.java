package kickstart.football;

import org.javamoney.moneta.Money;
import org.salespointframework.core.DataInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

import static org.salespointframework.core.Currencies.EURO;

@Component
@Order(3)
public class BettingDataInitializer implements DataInitializer{

	private static final Logger LOG = LoggerFactory.getLogger(BettingDataInitializer.class);

	private final BettingCatalog bettingCatalog;


	BettingDataInitializer(BettingCatalog bettingCatalog) {

		Assert.notNull(bettingCatalog, "bettingCatalog must not be null!");

		this.bettingCatalog = bettingCatalog;
	}

	@Override
	public void initialize() {

		if (bettingCatalog.findAll().iterator().hasNext()) {
			return;
		}

		LOG.info("Creating default betting catalog entries.");

		bettingCatalog.save(new FootballMatch("FCB-SCF", Money.of(1, EURO),
				LocalDateTime.of(2021, 11, 7, 15, 30),
				"FC Bayern", "SC Freiburg"));
		bettingCatalog.save(new FootballMatch("Wol-FCA", Money.of(1, EURO),
				LocalDateTime.of(2021, 11, 7, 15, 30),
				"Wolfsburg", "FC Augsburg"));
		bettingCatalog.save(new FootballMatch("RBL-BVB", Money.of(1, EURO),
				LocalDateTime.of(2021, 11, 7, 18, 30),
				"RB Leipzig", "Borussia Dortmund"));
	}
}

