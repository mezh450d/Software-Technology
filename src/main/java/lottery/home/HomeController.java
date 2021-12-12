package lottery.home;

import lottery.betting.BettingManagement;
import lottery.finance.FinanceManagement;
import lottery.home.message.Message;
import lottery.home.message.MessageManagement;
import lottery.user.UserManagement;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

	private final FinanceManagement financeManagement;
	private final BettingManagement bettingManagement;
	private final MessageManagement messageManagement;
	private final UserManagement userManagement;

	HomeController(FinanceManagement financeManagement, BettingManagement bettingManagement,
				   MessageManagement messageManagement, UserManagement userManagement) {
		this.financeManagement = financeManagement;
		this.bettingManagement = bettingManagement;
		this.messageManagement = messageManagement;
		this.userManagement = userManagement;
	}

	@GetMapping(path = "/home")
	String home(@LoggedIn UserAccount user, Model model) {

		model.addAttribute("balance", financeManagement.getUserBalance(user));
		model.addAttribute("bets", bettingManagement.findBetsByUser(user.getUsername()));
		model.addAttribute("username", user.getUsername());
		model.addAttribute("alert", messageManagement.newMessages(user.getUsername()));

		if(!user.getUsername().equals("boss")) {
			model.addAttribute("email_address", user.getEmail());
			model.addAttribute("lottery_address",
					userManagement.findByUserAccount(user).getLotteryAddress());
		}

		return "home";
	}

	@GetMapping(path = "/message")
	String message(@LoggedIn UserAccount user, Model model ) {

		model.addAttribute("alert", messageManagement.newMessages(user.getUsername()));
		model.addAttribute("newMessages", messageManagement.findNotReadByUser(user.getUsername()));
		model.addAttribute("oldMessages", messageManagement.findReadByUser(user.getUsername()));
		return "message";
	}

	@PostMapping(path = "/message")
	String readMessages(@LoggedIn UserAccount user) {

		for(Message message : messageManagement.findNotReadByUser(user.getUsername())){
			message.setRead();
			messageManagement.save(message);
		}
		return "redirect:/message";
	}
}
