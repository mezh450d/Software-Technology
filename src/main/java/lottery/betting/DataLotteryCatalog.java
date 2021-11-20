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
import org.salespointframework.catalog.Catalog;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Streamable;

public interface DataLotteryCatalog extends Catalog<LotteryEntity> {

	Sort DEFAULT_SORT = Sort.by("date").ascending();

	@Override
	Streamable<LotteryEntity> findAll();

	@Query
	Iterable<LotteryEntity> findByCategory(Category category, Sort sort);

	@Query
	default Iterable<LotteryEntity> findByCategory(Category category) {
		return findByCategory(category, DEFAULT_SORT);
	}
}
