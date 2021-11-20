package lottery.betting;

import lottery.betting.football.*;
import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.salespointframework.core.Currencies.EURO;

@Controller
class BettingController {

	private DataCatalog dataCatalog;
	private BetRepository bets;

	BettingController(DataCatalog dataCatalog, BetRepository bets) {
		this.dataCatalog = dataCatalog;
		this.bets = bets;
	}

	@GetMapping("/home")
	String home(@LoggedIn UserAccount user, Model model) {
		String userName = user.getUsername();
		model.addAttribute("bets", bets.findByUser(userName));
		return "home";
	}

	@GetMapping("/betting")
	public String betting() {
		return "betting";
	}

	@GetMapping("/number")
	public String number() {
		return "number";
	}

	@GetMapping("/football")
	String football(Model model) {
		model.addAttribute("matches", dataCatalog.findByCategory(Category.FOOTBALL));
		return "football";
	}

	@PostMapping("/football")
	String addBet(@LoggedIn UserAccount user, @RequestParam("match") FootballMatch match, @RequestParam("home_score") int homeScore,
				  @RequestParam("guest_score") int guestScore, @RequestParam("amount") int amount) {
		Bet bet = new Bet(user, match, new Score(homeScore, guestScore), Money.of(amount, EURO));
		bets.save(bet);
		return "redirect:/home";
	}
}
