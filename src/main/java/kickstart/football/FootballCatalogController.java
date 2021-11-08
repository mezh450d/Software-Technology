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

import org.salespointframework.quantity.Quantity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
;

@Controller
class FootballCatalogController {

	private static final Quantity NONE = Quantity.of(0);

	private final FootballCatalog footballCatalog;

	FootballCatalogController(FootballCatalog footballCatalog) {

		this.footballCatalog = footballCatalog;
	}

	@GetMapping("/football")
	String football(Model model) {

		model.addAttribute("catalog", footballCatalog.findByCategory(Category.BUNDESLIGA));
		model.addAttribute("title", "catalog.dvd.title");

		return "catalog";
	}
}
