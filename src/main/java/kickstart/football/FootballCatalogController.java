/*
 * Copyright 2013-2020 the original author or authors.
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
package kickstart.football;

import org.javamoney.moneta.Money;
import org.salespointframework.quantity.Quantity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
;import java.time.LocalDateTime;
import java.util.Arrays;

import static org.salespointframework.core.Currencies.EURO;

@Controller
class FootballCatalogController {

	private final FootballCatalog footballCatalog;

	FootballCatalogController(FootballCatalog footballCatalog) {
		this.footballCatalog = footballCatalog;
	}

	@GetMapping("/football")
	String football(FootballCatalog catalog, Model model) {
		//model.addAttribute("matches", catalog.findByCategory(Category.BUNDESLIGA));
		model.addAttribute("matches", Arrays.asList(
				new FootballMatch("FCB-SCF", Money.of(1, EURO),
						LocalDateTime.of(2021, 11, 7, 15, 30),
						Category.BUNDESLIGA, "FC Bayern", "SC Freiburg"),
				new FootballMatch("Wol-FCA", Money.of(1, EURO),
						LocalDateTime.of(2021, 11, 7, 15, 30),
						Category.BUNDESLIGA,"Wolfsburg", "FC Augsburg"),
				new FootballMatch("RBL-BVB", Money.of(1, EURO),
						LocalDateTime.of(2021, 11, 7, 18, 30),
						Category.BUNDESLIGA,"RB Leipzig", "Borussia Dortmund")
				));
		return "football";
	}
}
