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
import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.salespointframework.core.Currencies.EURO;

@Controller
class BettingController {

	private final BettingManagement management;
	private final CommunityManagement communityManagement;
	private final FinanceManagement financeManagement;

	BettingController(BettingManagement management, CommunityManagement communityManagement,
					  FinanceManagement financeManagement) {
		this.management = management;
		this.communityManagement = communityManagement;
		this.financeManagement = financeManagement;
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
				return "redirect:/home?error";
			}
		} else {
			if(financeManagement.withdraw(financeForm, user)){
				management.saveCommunityBet(new CommunityBet(match, new Score(homeScore, guestScore),
						community, user, Money.of(amount, EURO)));
			} else {
				return "redirect:/home?error";
			}
		}

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
}
