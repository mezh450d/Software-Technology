package lottery.finance;

import javax.validation.Valid;

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class FinanceController {

	private final FinanceRepository finances;
	FinanceController(FinanceRepository finances) {
		this.finances = finances;
	}
	private Money balance;
	@GetMapping(path = "/finances")
	String financesOverview(@LoggedIn UserAccount user, Model model,FinanceForm form) {
		model.addAttribute("balance",Money.of(0.0,"EUR"));
		String userName = user.getUsername();
		if(finances.count() > 0){
			List<FinanceEntry> allFinance = finances.findAll().toList();
			List<FinanceEntry> personalFinances = new ArrayList<>();
			List<Double> personalAmounts = new ArrayList<>();
			for(FinanceEntry finance : allFinance){
				if(finance.getUser().equals(userName)) {
					personalFinances.add(finance);
					personalAmounts.add(finance.getAmount());
					finance.setBalance(Money.of(0.0, "EUR"));
					Iterable<Double> amountsWithoutSign = personalAmounts;
					Iterator<Double> iterator = amountsWithoutSign.iterator() ;
					while (iterator.hasNext()) {
						double temp = iterator.next();
						finance.setBalance(finance.getBalance().add(Money.of(temp,"EUR")));
					}
					balance = finance.getBalance();
					model.addAttribute("entries", personalFinances);
					model.addAttribute("balance", balance);
				}
			}
		}
		model.addAttribute("form", form);

		return "finances";

	}


	@PostMapping(path = "/finances")
	String depositAndWithdraw(@Valid @ModelAttribute("form") FinanceForm form, Errors errors, Model model,
							  @RequestParam(value = "action") String action, @LoggedIn UserAccount user) {
		String userName = user.getUsername();
		if (errors.hasErrors() ) {
			return financesOverview(user, model, form);
		}
		if (action.equals("deposit")) {
			form.addAmount(form.getAmount());
			FinanceEntry entry = new FinanceEntry(user, form.getAmount(),form.getNote(), form.getDate(), form.getBalance());
			finances.save(entry);
			model.addAttribute("entry", entry);
		}
		if (action.equals("withdraw")) {
			if (balance.isGreaterThanOrEqualTo(Money.of(form.getAmount(),"EUR"))){
				form.addAmount(-(form.getAmount()));
				FinanceEntry entry = new FinanceEntry(user, -(form.getAmount()),form.getNote(), form.getDate(),form.getBalance());
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

