package lottery.home;

import lottery.betting.BettingManagement;
import lottery.finance.FinanceManagement;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	private final FinanceManagement financeManagement;
	private final BettingManagement bettingManagement;

	HomeController(FinanceManagement financeManagement, BettingManagement bettingManagement) {
		this.financeManagement = financeManagement;
		this.bettingManagement = bettingManagement;
	}

	@GetMapping(path = "/home")
	String home(@LoggedIn UserAccount user, Model model) {

		model.addAttribute("balance", financeManagement.getUserBalance(user));
		model.addAttribute("bets", bettingManagement.findBetsByUser(user.getUsername()));
		model.addAttribute("username", user.getUsername());
		model.addAttribute("email_address", user.getEmail());
		model.addAttribute("lottery_address", "0123456789");

		return "home";
	}
}
