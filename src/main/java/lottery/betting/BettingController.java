package lottery.betting;

import lottery.betting.football.*;
import lottery.betting.number.LotteryEntity;
import lottery.betting.number.SelectNumber;
import lottery.finance.FinanceEntry;
import lottery.finance.FinanceForm;
import lottery.finance.FinanceRepository;
import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

import static org.salespointframework.core.Currencies.EURO;

@Controller
class BettingController {

	private final DataCatalog dataCatalog;
	private final BetRepository bets;
	FinanceRepository finances;
	BettingController(DataCatalog dataCatalog, BetRepository bets, FinanceRepository finances) {
		this.dataCatalog = dataCatalog;
		this.bets = bets;
		this.finances = finances;
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

	@GetMapping("/lotteryViewList")
	String lotteryViewList(Model model, String productIdListStr) {
		productIdListStr = productIdListStr.substring(0, productIdListStr.length() - 1);
		model.addAttribute("productIdListStr", productIdListStr);
		return "number2";
	}

	@PostMapping("/football")
	String addBet(@LoggedIn UserAccount user, @RequestParam("match") FootballMatch match, @RequestParam("home_score") int homeScore,
				  @RequestParam("guest_score") int guestScore, @RequestParam("amount") double amount) {
		Money balance =Money.of(0.0,"EUR");
		if (finances.count() > 0) {
			List<FinanceEntry> allFinances = finances.findByUser(user.getUsername()).toList();
			for (FinanceEntry financeEntry : allFinances) {
				if (financeEntry.getUser().equals(user.getUsername())) {
					balance = financeEntry.getBalance();
				}
			}
		}
		if(Money.of(amount, EURO).isLessThanOrEqualTo(balance)){
			bets.save(new Bet(user, match, new Score(homeScore, guestScore), Money.of(amount, EURO)));
			FinanceEntry entry = new FinanceEntry(user ,-amount ,"Fußball-Abbuchung", LocalDateTime.now(), balance.subtract(Money.of(amount, EURO)));
			finances.save(entry);
			return "redirect:/home";
		}
		return "redirect:/football";
	}

	@PostMapping("/lottery")
	String addBet(@LoggedIn UserAccount user, @RequestParam("lottery") LotteryEntity lottery, @RequestParam("numStr") String numStr
			, @RequestParam("superzahl") int superNumber, @RequestParam("menge") double menge) {
		Money balance =Money.of(0.0,"EUR");
		if (finances.count() > 0) {
			List<FinanceEntry> allFinances = finances.findAll().toList();
			for (FinanceEntry financeEntry : allFinances) {
				if (financeEntry.getUser().equals(user.getUsername())) {
					balance = financeEntry.getBalance();
				}
			}
		}
		if(Money.of(menge, EURO).isLessThanOrEqualTo(balance)){
			bets.save(new Bet(user, lottery, new SelectNumber(numStr,superNumber), Money.of(menge, EURO)));
			FinanceEntry entry = new FinanceEntry(user, -menge ,"Lotterie-Abbuchung", LocalDateTime.now(), balance.subtract(Money.of(menge, EURO)));
			finances.save(entry);
			return "redirect:/home";
		}
		return "redirect:/lottery";
	}
}
