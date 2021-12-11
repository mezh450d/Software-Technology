package lottery.betting;

import lottery.betting.bet.CommunityBet;
import lottery.betting.bet.IndividualBet;
import lottery.betting.bet.Type;
import lottery.betting.data.Category;
import lottery.betting.data.Data;
import lottery.betting.data.football.*;
import lottery.betting.data.number.SelectNumber;
import lottery.community.CommunityManagement;
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
	private final CommunityManagement communityManagement;
	private final FinanceManagement financeManagement;
	private final MessageManagement messageManagement;


	BettingController(BettingManagement management, CommunityManagement communityManagement,
					  FinanceManagement financeManagement, MessageManagement messageManagement) {
		this.management = management;
		this.communityManagement = communityManagement;
		this.financeManagement = financeManagement;
		this.messageManagement = messageManagement;
	}

	@GetMapping("/betting")
	public String betting() {
		return "betting";
	}

	@GetMapping("/betting/number")
	public String number(@LoggedIn UserAccount user, Model model) {
		model.addAttribute("personalCommunities", communityManagement.findPersonalCommunities(user));

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
	public String football(@LoggedIn UserAccount user, Model model) {
		model.addAttribute("matches", management.findDataByCategory(Category.FOOTBALL));
		model.addAttribute("personalCommunities", communityManagement.findPersonalCommunities(user));

		return "betting_football";
	}

	@PostMapping("/betting/football")
	public String addBet(@LoggedIn UserAccount user, @RequestParam("match") FootballMatch match,
				  @RequestParam("home_score") int homeScore, @RequestParam("guest_score") int guestScore,
				  @RequestParam("amount") int amount, @RequestParam("dropCommunity") String community) {

		FinanceForm financeForm = new FinanceForm((double)amount, "Wettplatzierung zu "+match.toString());

		if(community.isEmpty()){
			if(financeManagement.withdraw(financeForm, user)){
				management.saveIndividualBet(new IndividualBet(match, new Score(homeScore, guestScore),
						user, Money.of(amount, EURO)));
			} else {
				Message message = new Message(user, "2 Euro Bußgeld",
						"Sie haben einen niedrigen Saldo im Wetten von " + match.toString(),
						LocalDateTime.now());
				messageManagement.save(message);
				FinanceForm form = new FinanceForm(2.0, "Mahnung: " + match.toString());
				financeManagement.withdraw(form, user);
				return "redirect:/home?error";
			}
		} else {
			if(financeManagement.withdraw(financeForm, user)){
				management.saveCommunityBet(new CommunityBet(match, new Score(homeScore, guestScore),
						community, user, Money.of(amount, EURO)));
			} else {
				Message message = new Message(user, "2 Euro Bußgeld",
						"Sie haben einen niedrigen Saldo im Wetten von " + match.toString(),
						LocalDateTime.now());
				messageManagement.save(message);
				FinanceForm form = new FinanceForm(2.0, "Mahnung: " + match.toString());
				financeManagement.withdraw(form, user);
				return "redirect:/home?error";
			}
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
	public String addBet(@LoggedIn UserAccount user, @RequestParam("numStr") String numStr,
				  @RequestParam("superNumber") int superNumber, @RequestParam("amount") int amount,
				  @RequestParam("community") String community) {

		if(amount <= 0){
			return "redirect:/home?error";
		}

		int realAmount = 10 * amount;
		Data firstLottery = management.findNextLottery();

		FinanceForm financeForm = new FinanceForm((double)realAmount,
				"Wettplatzierung für Lotto 6 aus 49 ("+amount+"x)");

		if(community.isEmpty()){
			if(financeManagement.withdraw(financeForm, user)){
				for(Data lottery : management.findDataByCategory(Category.LOTTERY)){
					if(amount > 0){
						management.saveIndividualBet(new IndividualBet(lottery, new SelectNumber(numStr,superNumber),
								user, Money.of(10, EURO)));
						amount--;
					}
				}
			} else {
				Message message = new Message(user, "2 Euro Bußgeld",
						"Sie haben einen niedrigen Saldo im Wetten von 6 aus 49 ("+amount+"x)",
						LocalDateTime.now());
				messageManagement.save(message);
				FinanceForm form = new FinanceForm(2.0, "Mahnung: für Lotto 6 aus 49 ("+amount+"x)");
				financeManagement.withdraw(form, user);
				return "redirect:/home?error";
			}
		} else {
			if(financeManagement.withdraw(financeForm, user)){
				for(Data lottery : management.findDataByCategory(Category.LOTTERY)){
					if(amount > 0){
						{
							management.saveCommunityBet(new CommunityBet(lottery, new SelectNumber(numStr,superNumber),
									community, user, Money.of(10, EURO)));
						}
						amount--;
					}
				}
			} else {
				Message message = new Message(user, "2 Euro Bußgeld",
						"Sie haben einen niedrigen Saldo im Wetten von 6 aus 49 ("+amount+"x)",
						LocalDateTime.now());
				messageManagement.save(message);
				FinanceForm form = new FinanceForm(2.0, "Mahnung: für Lotto 6 aus 49 ("+amount+"x)");
				financeManagement.withdraw(form, user);
				return "redirect:/home?error";
			}
		}
		return "redirect:/home";
	}

	@PostMapping("/community/bet")
	public String addAmountToBet(@LoggedIn UserAccount user, @RequestParam("bet") long betID,
								 @RequestParam("amount") int amount, Model model) {

		CommunityBet bet = management.findByCommunityBetId(betID);

		FinanceForm financeForm = new FinanceForm((double)amount,
				"Wettplatzierung zu "+bet.getReference().toString());

		if(financeManagement.withdraw(financeForm, user)){
			bet.addUserToBet(user, Money.of(amount, EURO));
		} else {
			return "redirect:/home?error";
		}

		model.addAttribute("personalCommunities", communityManagement.findPersonalCommunities(user));
		model.addAttribute("joinableCommunities", communityManagement.findJoinableCommunities(user));

		return "redirect:/community";
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
