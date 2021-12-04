package lottery.betting;

import lottery.betting.football.*;
import lottery.betting.number.LotteryEntity;
import lottery.betting.number.SelectNumber;
import lottery.finance.FinanceForm;
import lottery.finance.FinanceManagement;
import lottery.message.Message;
import lottery.message.MessageManagement;
import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.data.util.Streamable;
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

	private final BettingManagement management;
	private final FinanceManagement financeManagement;
	private final MessageManagement messageManagement;
	private final DataCatalog dataCatalog;

	BettingController(BettingManagement management, FinanceManagement financeManagement, MessageManagement messageManagement, DataCatalog dataCatalog) {
		this.management = management;
		this.financeManagement = financeManagement;
		this.messageManagement = messageManagement;
		this.dataCatalog = dataCatalog;
	}

	@GetMapping("/betting")
	public String betting() {
		return "betting";
	}

	@GetMapping("/betting/number")
	public String number() {
		return "betting_number";
	}

	@GetMapping("/betting/updateLotteryList")
	public String updateLotteryList(@LoggedIn UserAccount userAccount, Model model) {
		Streamable<Bet> betsByUser = management.findBetsByUser(userAccount.getUsername());
		List<Bet> bets = betsByUser.toList();
		model.addAttribute("betList", bets);
		return "bettingUpdateLotteryList";
	}

	@GetMapping("/betting/updateLotteryView")
	public String updateLotteryView(@LoggedIn UserAccount userAccount, Long id, Integer category, String productId, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("productId", productId);
		if (category == Category.LOTTERY.ordinal()) {
			return "updateLotteryView";
		} else {
			Streamable<Bet> betsByUser = management.findBetsByUser(userAccount.getUsername());
			List<Bet> bets = betsByUser.toList();
			for (Bet bet : bets) {
				if (bet.getId() == id) {
					model.addAttribute("match", bet);
				}
			}
			return "updateFootballView";
		}
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
			Message message = new Message(user, "Mahnung von Fußball", match.toString(), Money.of(-2.0,"EUR"), LocalDateTime.now());
			messageManagement.save(message);
			FinanceForm form = new FinanceForm(2.0, "Mahnung: " + match.toString());
			financeManagement.withdraw(form, user);
			return "redirect:/home?error";
		}
		return "redirect:/home";
	}

	@PostMapping("/betting/updateFootball")
	String addBet(@LoggedIn UserAccount user, @RequestParam("match") FootballMatch match, Long id,
				  @RequestParam("home_score") int homeScore, @RequestParam("guest_score") int guestScore,
				  @RequestParam("amount") int amount) {

		Bet bet = new Bet(user, match, new Score(homeScore, guestScore), Money.of(amount, EURO));
		bet.setId(id);
		management.saveBet(bet);

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
			Message message = new Message(user, "Mahnung von 6 aus 49", "Mahnung: für Lotto 6 aus 49 ("+amount+"x)", Money.of(-2.0,"EUR"), LocalDateTime.now());
			messageManagement.save(message);
			FinanceForm form = new FinanceForm(2.0, "Mahnung: für Lotto 6 aus 49 ("+amount+"x)");
			financeManagement.withdraw(form, user);
			return "redirect:/home?error";
		}
		return "redirect:/home";
	}

	@PostMapping("/betting/updateLottery")
	String updateLottery(@LoggedIn UserAccount user, Long id, @RequestParam("lottery") LotteryEntity lottery, @RequestParam("numStr") String numStr, @RequestParam("superNumber") int superNumber) {
		int provisionalAmount = 10;
		Bet bet = new Bet(user, lottery, new SelectNumber(numStr,superNumber), Money.of(provisionalAmount, EURO));
		bet.setId(id);
		management.saveBet(bet);
		return "redirect:/home";
	}
}