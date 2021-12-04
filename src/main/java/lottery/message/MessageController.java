package lottery.message;

import lottery.betting.Bet;
import lottery.betting.BettingManagement;
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

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.salespointframework.core.Currencies.EURO;

@Controller
public class MessageController {
	private final MessageManagement management;

	MessageController(MessageManagement management) {
		this.management = management;
	}

	@GetMapping(path = "/message")
	String message(@LoggedIn UserAccount user, Model model ) {
		model.addAttribute("messages", management.findEntriesByUser(user.getUsername()));
		return "message";
	}

//	@PostMapping(path = "//betting/football")
//	String addMessage(@LoggedIn UserAccount user, @RequestParam("lottery") LotteryEntity lottery,
//			   @RequestParam("numStr") String numStr, @RequestParam("superNumber") int superNumber,
//			   @RequestParam("amount") int amount) {
//
//		int provisionalAmount = 10;
//
//		FinanceForm financeForm = new FinanceForm((double)provisionalAmount, "Wettplatzierung zu "+lottery.toString());
//
//		if(financeManagement.withdraw(financeForm, user)){
//			bettingManagement.saveBet(new Bet(user, lottery, new SelectNumber(numStr,superNumber), Money.of(provisionalAmount, EURO)));
//		} else {
//			Message message = new Message(user, "Mahnung", Money.of(-2.0,"EUR"), LocalDateTime.now(Clock.offset(Clock.systemUTC(), Duration.ofHours(1))));
//			management.save(message);
//			FinanceForm form = new FinanceForm(2.0, "Mahnung: ");
//			financeManagement.withdraw(form, user);
//			return "redirect:/home?error";
//		}
//		return "redirect:/home";
//	}
//	}
}

