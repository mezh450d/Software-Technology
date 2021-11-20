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
package lottery.betting.football;

import lottery.betting.Category;
import lottery.betting.Data;
import lottery.betting.Result;
import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;

import javax.persistence.Entity;
import java.time.LocalDateTime;


@Entity
public class FootballMatch extends Data {

	private String home, guest;

	@SuppressWarnings({ "unused", "deprecation" })
	protected FootballMatch() {
		super();
	}

	public FootballMatch(String name, Money price, LocalDateTime date, Category category, String home, String guest) {

		super(name, price, date, category);

		this.home = home;
		this.guest = guest;
	}

	public void setScore(int homeScore, int guestScore) {
		if (isSet()) return;
		setResult(new Score(homeScore, guestScore));
		set();
	}

	public String getHome() {
		return home;
	}

	public String getGuest() {
		return guest;
	}

	@Override
	public String toString(){
		return guest+" @ "+home+" on "+ getDate();
	}

}
