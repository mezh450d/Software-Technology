package lottery.finance;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FinanceController {

	private final FinanceRepository finance;
	FinanceController(FinanceRepository finance) {
		this.finance = finance;
	}

	@GetMapping(path = "/finances")
 	String finances(Model model, FinanceForm form) {

		FinanceEntry ff = FinanceForm.ALL_AMOUNT.get(form.getId());
		Double balance = 0.0;
		if(ff != null) {
			balance = ff.getBalance();
		}

		model.addAttribute("entries", finance.findAll());
		model.addAttribute("form", form);
		model.addAttribute("balance", balance);

		return "finances";
		}

	@PostMapping(path = "/finances")
	String depositAndWithdraw(@Valid @ModelAttribute("form") FinanceForm form, Errors errors, Model model,
							  @RequestParam(value = "action") String action) {

		if (errors.hasErrors() ) {
			return finances(model, form);
		}
		if (action.equals("deposit")) {
			form.addAmount(form.getAmount());
			finance.save(form.toNewEntry());
		}
		if (action.equals("withdraw")) {
			if (form.calculateBalance() >= form.getAmount()){
				form.addAmount(-(form.getAmount()));
				finance.save(form.toNewEntry1());
			}
		}
		form.calculateBalance();
		return "redirect:/finances";
	}

	@PostMapping(path = "/finances", headers = "X-Requested-With=XMLHttpRequest")
	String finance(@Valid FinanceForm form, Model model) {

		model.addAttribute("entry", finance.save(form.toNewEntry()));
		model.addAttribute("index", finance.count());
		FinanceEntry ff = FinanceForm.ALL_AMOUNT.get(form.getId());
		Double balance = 0.0;
		if(ff != null) {
			balance = ff.getBalance();
		}
		model.addAttribute("balance", balance);
		return "finances :: entry";
	}

}

