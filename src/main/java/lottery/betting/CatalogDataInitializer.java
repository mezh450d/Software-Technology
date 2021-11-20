/*
 * Copyright 2013-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lottery.betting;

import lottery.betting.football.FootballMatch;
import lottery.betting.number.LotteryEntity;
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
@Order(20)
class CatalogDataInitializer implements DataInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(CatalogDataInitializer.class);

	private final DataCatalog catalog;
	private final DataLotteryCatalog dataLotteryCatalog;

	CatalogDataInitializer(DataCatalog catalog, DataLotteryCatalog dataLotteryCatalog) {

		Assert.notNull(catalog, "FootballCatalog must not be null!");

		this.catalog = catalog;
		this.dataLotteryCatalog = dataLotteryCatalog;
	}

	@Override
	public void initialize() {

		if (!catalog.findAll().iterator().hasNext()) {
			LOG.info("Creating default catalog entries.");

			catalog.save(new FootballMatch("FCB-SCF", Money.of(1, EURO),
					LocalDateTime.of(2021, 11, 7, 15, 30),
					Category.FOOTBALL, "FC Bayern", "SC Freiburg"));
			catalog.save(new FootballMatch("Wol-FCA", Money.of(1, EURO),
					LocalDateTime.of(2021, 11, 7, 15, 30),
					Category.FOOTBALL,"Wolfsburg", "FC Augsburg"));
			catalog.save(new FootballMatch("RBL-BVB", Money.of(1, EURO),
					LocalDateTime.of(2021, 11, 7, 18, 30),
					Category.FOOTBALL,"RB Leipzig", "Borussia Dortmund"));
		}



		if (!dataLotteryCatalog.findAll().iterator().hasNext()) {
			dataLotteryCatalog.save(new LotteryEntity("one", Money.of(1, EURO),
					LocalDateTime.of(2021, 12, 12, 10, 28),
					Category.LOTTERY, "1,2,3,4,5,6", "5"));
			dataLotteryCatalog.save(new LotteryEntity("two", Money.of(1, EURO),
					LocalDateTime.of(2021, 12, 19, 13, 10),
					Category.LOTTERY,"22,23,24,25,26,27", "1"));
			dataLotteryCatalog.save(new LotteryEntity("three", Money.of(1, EURO),
					LocalDateTime.of(2021, 12, 26, 15, 25),
					Category.LOTTERY,"31,32,33,34,35,36", "2"));
		}

	}
}
