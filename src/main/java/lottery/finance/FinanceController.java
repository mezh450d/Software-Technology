package lottery.finance;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import lottery.betting.Bet;
import lottery.betting.BetRepository;
import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lottery.betting.football.*;

@Controller
public class FinanceController {

	private final FinanceRepository finances;
	public static Money balance =  Money.of(0.0,"EUR");;
	private final BetRepository bets;

	FinanceController(FinanceRepository finances, BetRepository bets) {
		this.finances = finances;
		this.bets = bets;
	}

	@GetMapping(path = "/finances")
	String financesOverview(@LoggedIn UserAccount user, Model model, FinanceForm form) {
		String userName = user.getUsername();
		model.addAttribute("entries", finances.findByUser(userName));
		FinanceEntry financeEntry = new FinanceEntry();
		balance =  Money.of(0.0,"EUR");
		if(finances.count() > 0){
			List<FinanceEntry> allFinance = finances.findAll().toList();
			for(FinanceEntry finance : allFinance){
				if(finance.getUser().equals(userName)) {
					form.personalAmounts.add(finance.getAmount());
					form.setBalance(Money.of(0.0,"EUR"));
					balance = form.calculateBalance(form.personalAmounts);
				}
			}
		}

		form.setBalance(balance);
		model.addAttribute("balance", balance.toString());
		model.addAttribute("form", form);
		return "finances";
	}


	@PostMapping(path = "/finances")
	String depositAndWithdraw(@Valid @ModelAttribute("form") FinanceForm form, Errors errors, Model model,
							  @RequestParam(value = "action") String action, @LoggedIn UserAccount user) {

		if (errors.hasErrors() ) {
			return financesOverview(user, model, form);
		}
		if (action.equals("deposit")) {
			form.addAmount(form.getAmount());
			FinanceEntry entry = new FinanceEntry(user, form.getAmount(),form.getNote(), form.getDate(), balance.add(Money.of(form.getAmount(),"EUR")));
			finances.save(entry);
			model.addAttribute("entry", entry);
		}
		if (action.equals("withdraw")) {
			if (balance.isGreaterThanOrEqualTo(Money.of( form.getAmount(),"EUR"))){
				form.addAmount(-(form.getAmount()));
				FinanceEntry entry = new FinanceEntry(user, -(form.getAmount()),form.getNote(), form.getDate(), balance.subtract(Money.of(form.getAmount(),"EUR")));
				finances.save(entry);
				model.addAttribute("entry", entry);
			}
		}

		return "redirect:/finances";
	}

	@PostMapping(path = "/finances", headers = "X-Requested-With=XMLHttpRequest")
	String finance(@LoggedIn UserAccount user, @Valid FinanceForm form, Model model) {

		FinanceEntry entry = new FinanceEntry(user, form.getAmount(),form.getNote(), form.getDate(), form.getBalance());
		finances.save(entry);
		model.addAttribute("entry", entry);
		model.addAttribute("index", finances.count());
		return "finances :: entry";
	}

}

