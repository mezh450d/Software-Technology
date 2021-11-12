package kickstart.football;

import kickstart.lottery.user.User;
import org.javamoney.moneta.Money;
import org.salespointframework.order.Cart;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@PostMapping("/home")
	String addBet(@LoggedIn User user, @RequestParam("id") FootballMatch match, @RequestParam("home_score") int homeScore,
				  @RequestParam("guest_score") int guestScore, @RequestParam("amount") int amount, @ModelAttribute Model model) {

		return "home";
	}

	}
