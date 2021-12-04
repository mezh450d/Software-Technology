package lottery.betting;

import lottery.betting.football.*;
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

	@GetMapping("/betting")
	public String betting() {
		return "betting";
	}

	@GetMapping("/betting/number")
	public String number() {
		return "betting_number";
	}

	@GetMapping("/betting/football")
	String football(Model model) {
		model.addAttribute("matches", management.findDataByCategory(Category.FOOTBALL));
		return "betting_football";
	}

	@PostMapping("/betting/football")
	String addBet(@LoggedIn UserAccount user, @RequestParam("match") FootballMatch match,
				  @RequestParam("home_score") int homeScore, @RequestParam("guest_score") int guestScore,
				  @RequestParam("amount") int amount) {

		FinanceForm financeForm = new FinanceForm((double)amount, "Wettplatzierung zu "+match.toString());

		if(financeManagement.withdraw(financeForm, user)){
			management.saveBet(new Bet(user, match, new Score(homeScore, guestScore), Money.of(amount, EURO)));
		} else {
			return "redirect:/home?error";
		}
		return "redirect:/home";
	}

	@PostMapping("/betting/number")
	String addBet(@LoggedIn UserAccount user, @RequestParam("numStr") String numStr,
				  @RequestParam("superNumber") int superNumber, @RequestParam("amount") int amount) {

		if(amount <= 0){
			return "redirect:/home?error";
		}

		int realAmount = 10 * amount;
		Data firstLottery = management.findNextLottery();

		FinanceForm financeForm = new FinanceForm((double)realAmount,
				"Wettplatzierung für Lotto 6 aus 49 ("+amount+"x)");

		if(financeManagement.withdraw(financeForm, user)){
			for(Data lottery : management.findDataByCategory(Category.LOTTERY)){
				if(amount > 0){
					management.saveBet(new Bet(user, lottery, new SelectNumber(numStr,superNumber),
							Money.of(10, EURO)));
					amount--;
				}
			}
		} else {
			return "redirect:/home?error";
		}
		return "redirect:/home";
	}
}
