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

import java.util.ArrayList;
import java.util.List;

import static org.salespointframework.core.Currencies.EURO;

@Controller
class BettingController {

	private FootballCatalog footballCatalog;
	private BetRepository bets;

	BettingController(FootballCatalog footballCatalog, BetRepository bets) {
		this.footballCatalog = footballCatalog;
		this.bets = bets;
	}

	@GetMapping("/home")
	String home(@LoggedIn UserAccount user, Model model) {
		String userName = user.getUsername();
		if(bets.count() > 0){
			List<FootballBet> allBets = bets.findAll().toList();
			List<FootballBet> personalBets = new ArrayList<>();
			for(FootballBet bet : allBets){
				if(bet.getUser().equals(userName)){
					personalBets.add(bet);
				}
			}
			model.addAttribute("bets", personalBets);
		}
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
		model.addAttribute("matches", footballCatalog.findByCategory(Category.BUNDESLIGA));
		return "football";
	}

	@PostMapping("/football")
	String addBet(@LoggedIn UserAccount user, @RequestParam("match") FootballMatch match, @RequestParam("home_score") int homeScore,
				  @RequestParam("guest_score") int guestScore, @RequestParam("amount") int amount) {
		FootballBet bet = new FootballBet(user, match, new Score(homeScore, guestScore), Money.of(amount, EURO));
		bets.save(bet);
		return "redirect:/home";
	}
}
