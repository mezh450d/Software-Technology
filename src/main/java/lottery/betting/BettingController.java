package lottery.betting;

import lottery.betting.football.*;
import lottery.betting.number.LotteryEntity;
import lottery.betting.number.SelectNumber;
import lottery.finance.FinanceForm;
import lottery.finance.FinanceManagement;
import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.salespointframework.core.Currencies.EURO;

@Controller
class BettingController {

	private final BettingManagement management;
	private final FinanceManagement financeManagement;

	BettingController(BettingManagement management, FinanceManagement financeManagement) {
		this.management = management;
		this.financeManagement = financeManagement;
	}

	@GetMapping("/home")
	String home(@LoggedIn UserAccount user, Model model) {
		String userName = user.getUsername();
		model.addAttribute("bets", management.findBetsByUser(userName));
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
		model.addAttribute("matches", management.findDataByCategory(Category.FOOTBALL));
		return "football";
	}

	@GetMapping("/lotteryList")
	String lotteryList(Model model) {
		model.addAttribute("lotteryList", management.findDataByCategory(Category.LOTTERY));
		return "lotteryList";
	}

	@GetMapping("/lotteryView")
	String lotteryView(Model model, String productId) {
		model.addAttribute("productId", productId);
		return "number";
	}

	@PostMapping("/football")
	String addBet(@LoggedIn UserAccount user, @RequestParam("match") FootballMatch match,
				  @RequestParam("home_score") int homeScore, @RequestParam("guest_score") int guestScore,
				  @RequestParam("amount") int amount) {

		FinanceForm financeForm = new FinanceForm((double)amount, "Wettplatzierung zu "+match.toString());

		if(financeManagement.withdraw(financeForm, user)){
			management.saveBet(new Bet(user, match, new Score(homeScore, guestScore), Money.of(amount, EURO)));
			return "redirect:/home";
		} else {
			return "redirect:/football";
		}
	}

	@PostMapping("/lotteryView")
	String addBet(@LoggedIn UserAccount user, @RequestParam("lottery") LotteryEntity lottery,
				  @RequestParam("numStr") String numStr, @RequestParam("superNumber") int superNumber,
				  @RequestParam("amount") int amount) {

		int provisionalAmount = 10;

		FinanceForm financeForm = new FinanceForm((double)provisionalAmount, "Wettplatzierung zu "+lottery.toString());

		if(financeManagement.withdraw(financeForm, user)){
			management.saveBet(new Bet(user, lottery, new SelectNumber(numStr,superNumber), Money.of(provisionalAmount, EURO)));
			return "redirect:/home";
		} else {
			return "redirect:/lotteryView";
		}
	}
}
