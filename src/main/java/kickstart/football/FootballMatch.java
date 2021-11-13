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
import org.salespointframework.catalog.Product;
import org.salespointframework.catalog.ProductIdentifier;

import javax.persistence.Entity;
import java.time.LocalDateTime;


@Entity
public class FootballMatch extends Product {

	private String home, guest;
	private LocalDateTime date;
	private Category category;
	private int homeScore, guestScore;
	private Boolean set = false;

	@SuppressWarnings({ "unused", "deprecation" })
	private FootballMatch() {}

	public FootballMatch(String name, Money price, LocalDateTime date, Category category, String home, String guest) {

		super(name, price);

		this.date = date;
		this.category = category;
		this.home = home;
		this.guest = guest;
	}
	public LocalDateTime getDate() {
		return date;
	}

	public Score getResult() {
		return new Score(homeScore, guestScore);
	}

	public void setResult(Score result) {
		if (set) return;
		this.homeScore = result.getHome();
		this.guestScore = result.getGuest();
		set = true;
	}

	public String getHome() {
		return home;
	}

	public String getGuest() {
		return guest;
	}

	public Boolean isSet() { return set; }

	@Override
	public String toString(){
		return guest+" @ "+home+" on "+date.toString();
	}

}
