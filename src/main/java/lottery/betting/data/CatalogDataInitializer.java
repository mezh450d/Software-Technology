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
package lottery.betting.data;

import lottery.betting.data.football.FootballMatch;
import lottery.betting.data.football.rest.RestAPIParser;
import lottery.betting.data.number.LotteryEntity;
import org.apache.tomcat.jni.Local;
import org.javamoney.moneta.Money;
import org.salespointframework.core.DataInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.salespointframework.core.Currencies.EURO;


@Component
@Order(20)
class CatalogDataInitializer implements DataInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(CatalogDataInitializer.class);

	private final DataCatalog catalog;

	private final RestAPIParser parser;

	CatalogDataInitializer(DataCatalog catalog, RestAPIParser parser) {
		Assert.notNull(catalog, "DataCatalog must not be null!");
		this.catalog = catalog;
		this.parser = parser;
	}

	@Override
	public void initialize() {

		if (!catalog.findAll().iterator().hasNext()) {
			LOG.info("Creating default catalog entries.");

			//create FootballMatches
			for(FootballMatch match : parser.getNextMatchday()){
				catalog.save(match);
			}
			LOG.info("Retrieved data successfully from OpenLigaDB.");

			//create LotteryEntities
			LocalDateTime nextSunday = LocalDateTime.now();
			while(nextSunday.getDayOfWeek() != DayOfWeek.SUNDAY){
				nextSunday = nextSunday.plusDays(1);
			}

			LocalDateTime date = LocalDateTime.of(nextSunday.getYear(), nextSunday.getMonth(),
					nextSunday.getDayOfMonth(), 20,0);

			for(long i = 0; i < 69; i++){
				catalog.save(new LotteryEntity("Tippschein"+i, Money.of(10, EURO),
						date.plusWeeks(i), Category.LOTTERY, "Lotto 6 aus 49"));
			}
		}
	}
}
