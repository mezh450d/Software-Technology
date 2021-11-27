package lottery.betting;

import lottery.betting.football.*;
import lottery.betting.number.LotteryEntity;
import lottery.betting.number.SelectNumber;
import lottery.finance.FinanceEntry;
import lottery.finance.FinanceForm;
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

	private final DataCatalog dataCatalog;
	private final BetRepository bets;

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

	@GetMapping("/lotteryList")
	String lotteryList(Model model) {
		model.addAttribute("lotteryList", dataCatalog.findByCategory(Category.LOTTERY));
		return "lotteryList";
	}

	@GetMapping("/lotteryView")
	String lotteryView(Model model, String productId) {
		model.addAttribute("productId", productId);
		return "number";
	}

	@PostMapping("/football")
	String addBet(@LoggedIn UserAccount user, @RequestParam("match") FootballMatch match, @RequestParam("home_score") int homeScore,
				  @RequestParam("guest_score") int guestScore, @RequestParam("amount") int amount, FinanceForm form) {
		FinanceEntry financeEntry = FinanceForm.ALL_AMOUNT.get(form.getId());
		if(Money.of(amount, EURO).isLessThanOrEqualTo(financeEntry.getBalance())){
			bets.save(new Bet(user, match, new Score(homeScore, guestScore), Money.of(amount, EURO)));
			return "redirect:/home";
		}
		else return "redirect:/football";
	}

	@PostMapping("/lottery")
	String addBet(@LoggedIn UserAccount user, @RequestParam("lottery") LotteryEntity lottery, @RequestParam("numStr") String numStr
				  , @RequestParam("superNumber") int superNumber, @RequestParam("amount") int amount, FinanceForm form) {
		int provisionalAmount = 10;
		FinanceEntry financeEntry = FinanceForm.ALL_AMOUNT.get(form.getId());
		if(Money.of(provisionalAmount, EURO).isLessThanOrEqualTo(financeEntry.getBalance())){
			bets.save(new Bet(user, lottery, new SelectNumber(numStr,superNumber), Money.of(provisionalAmount, EURO)));
			return "redirect:/home";
		}
		else return "redirect:/lottery";
	}
}
