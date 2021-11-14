package kickstart.football;

import kickstart.lottery.user.User;
import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.salespointframework.core.Currencies.EURO;

@Controller
class FootballCatalogController {

	private FootballCatalog footballCatalog;
	private BetRepository bets;

	FootballCatalogController(FootballCatalog footballCatalog, BetRepository bets) {
		this.footballCatalog = footballCatalog;
		this.bets = bets;
	}

	@GetMapping("/football")
	String football(Model model) {
		model.addAttribute("matches", footballCatalog.findByCategory(Category.BUNDESLIGA));
		return "football";
	}

	@PostMapping("/football")
	String addBet(@LoggedIn User user, @RequestParam("mid") FootballMatch match, @RequestParam("home_score") int homeScore,
				  @RequestParam("guest_score") int guestScore, @RequestParam("amount") int amount) {
		FootballBet bet = new FootballBet(user, match, new Score(homeScore, guestScore), Money.of(amount, EURO));
		System.out.println("AFTER CREATION");
		System.out.println(bets.findAll().isEmpty());

		return "redirect:home";
	}

	}
