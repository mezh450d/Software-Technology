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

	CatalogDataInitializer(DataCatalog catalog) {
		Assert.notNull(catalog, "DataCatalog must not be null!");
		this.catalog = catalog;
	}

	@Override
	public void initialize() {

		if (!catalog.findAll().iterator().hasNext()) {
			LOG.info("Creating default catalog entries.");

			catalog.save(new FootballMatch("FCU-RBL", Money.of(1, EURO),
					LocalDateTime.of(2021, 12, 3, 20, 30),
					Category.FOOTBALL, "Union Berlin", "RB Leipzig"));
			catalog.save(new FootballMatch("Lev-Für", Money.of(1, EURO),
					LocalDateTime.of(2021, 12, 4, 15, 30),
					Category.FOOTBALL,"Bayer Leverkusen", "Greuther Fürth"));
			catalog.save(new FootballMatch("Bie-Köl", Money.of(1, EURO),
					LocalDateTime.of(2021, 12, 4, 15, 30),
					Category.FOOTBALL,"Arminia Bielefeld", "1. FC Köln"));
			catalog.save(new FootballMatch("M05-Wol", Money.of(1, EURO),
					LocalDateTime.of(2021, 12, 4, 15, 30),
					Category.FOOTBALL,"Mainz 05", "VfL Wolfsburg"));
			catalog.save(new FootballMatch("FCA-Vfl", Money.of(1, EURO),
					LocalDateTime.of(2021, 12, 4, 15, 30),
					Category.FOOTBALL,"FC Augsburg", "VfL Bochum"));
			catalog.save(new FootballMatch("TSG-SGE", Money.of(1, EURO),
					LocalDateTime.of(2021, 12, 4, 15, 30),
					Category.FOOTBALL,"TSG Hoffenheim", "Eintracht Frankfurt"));
			catalog.save(new FootballMatch("BVB-FCB", Money.of(1, EURO),
					LocalDateTime.of(2021, 12, 4, 18, 30),
					Category.FOOTBALL,"Borussia Dortmund", "FC Bayern München"));
			catalog.save(new FootballMatch("Stu-BSC", Money.of(1, EURO),
					LocalDateTime.of(2021, 12, 5, 15, 30),
					Category.FOOTBALL,"VfB Stuttgart", "Hertha BSC"));
			catalog.save(new FootballMatch("Gla-SCF", Money.of(1, EURO),
					LocalDateTime.of(2021, 12, 5, 17, 30),
					Category.FOOTBALL,"Borussia Mönchengladbach", "SC Freiburg"));

			LocalDateTime date = LocalDateTime.of(2021, 12, 5,20,0);
			for(long i = 0; i < 15; i++){
				catalog.save(new LotteryEntity("Tippschein", Money.of(10, EURO),
						date.plusWeeks(i), Category.LOTTERY, "Lotto 6 aus 49"));
			}
		}
	}
}
