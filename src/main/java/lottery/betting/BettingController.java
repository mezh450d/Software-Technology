package lottery.betting;

import lottery.Lottery;
import lottery.betting.football.*;
import lottery.betting.number.LotteryEntity;
import lottery.betting.number.ReturnResult;
import lottery.betting.number.SelectNumber;
import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.salespointframework.core.Currencies.EURO;

@Controller
class BettingController {

	private DataCatalog footballCatalog;
	private DataLotteryCatalog dataLotteryCatalog;
	private DataCatalog dataCatalog;
	private BetRepository bets;

	BettingController(DataCatalog footballCatalog, BetRepository bets, DataLotteryCatalog dataLotteryCatalog) {
//BettingController(DataCatalog dataCatalog, BetRepository bets) {
		this.footballCatalog = footballCatalog;

		this.dataCatalog = dataCatalog;
		this.bets = bets;
		this.dataLotteryCatalog = dataLotteryCatalog;
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
		model.addAttribute("lotteryList", dataLotteryCatalog.findByCategory(Category.LOTTERY));
		return "lotteryList";
	}

	@GetMapping("/lotteryView")
	String lotteryView(Model model, String productId) {
		model.addAttribute("productId", productId);
		return "number";
	}

	@PostMapping("/football")
	String addBet(@LoggedIn UserAccount user, @RequestParam("match") FootballMatch match, @RequestParam("home_score") int homeScore,
				  @RequestParam("guest_score") int guestScore, @RequestParam("amount") int amount) {
		Bet bet = new Bet(user, match, new Score(homeScore, guestScore), Money.of(amount, EURO));
		bets.save(bet);
		return "redirect:/home";
	}

	@PostMapping("/lottery")
	@ResponseBody
	ReturnResult addBet(@LoggedIn UserAccount user, @RequestParam("lottery") LotteryEntity lottery, @RequestParam("numStr") String numStr
				  , @RequestParam("superzahl") int superzahl, @RequestParam("menge") int menge) {
		Bet bet = new Bet(user, lottery, new SelectNumber(), Money.of(menge, EURO));
		bets.save(bet);
		return new ReturnResult();
	}
}
