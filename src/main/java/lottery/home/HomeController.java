package lottery.home;

import lottery.betting.BettingManagement;
import lottery.finance.FinanceManagement;
import lottery.user.UserManagement;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	private final FinanceManagement financeManagement;
	private final BettingManagement bettingManagement;
	private final UserManagement userManagement;

	HomeController(FinanceManagement financeManagement, BettingManagement bettingManagement,
				   UserManagement userManagement) {
		this.financeManagement = financeManagement;
		this.bettingManagement = bettingManagement;
		this.userManagement = userManagement;
	}

	@GetMapping(path = "/home")
	String home(@LoggedIn UserAccount user, Model model) {

		model.addAttribute("balance", financeManagement.getUserBalance(user));
		model.addAttribute("bets", bettingManagement.findBetsByUser(user.getUsername()));
		model.addAttribute("username", user.getUsername());

		if(!user.getUsername().equals("boss")) {
			model.addAttribute("email_address", user.getEmail());
			model.addAttribute("lottery_address",
					userManagement.findByUserAccount(user).getLotteryAddress());
		}

		return "home";
	}
}
