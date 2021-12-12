package lottery.admin;

import lottery.betting.bet.Bet;
import lottery.betting.BettingManagement;
import lottery.betting.data.Category;
import lottery.betting.data.football.FootballMatch;
import lottery.betting.data.football.Score;
import lottery.betting.data.number.LotteryEntity;
import lottery.betting.data.number.SelectNumber;
import lottery.community.Community;
import lottery.community.CommunityManagement;
import lottery.finance.FinanceForm;
import lottery.finance.FinanceManagement;
import lottery.home.message.MessageManagement;
import lottery.user.User;
import lottery.user.UserManagement;
import org.springframework.data.util.Streamable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class AdminController {

	private final UserManagement userManagement;
	private final FinanceManagement financeManagement;
	private final CommunityManagement communityManagement;
	private final BettingManagement bettingManagement;
	private final MessageManagement messageManagement;

	AdminController(UserManagement userManagement, FinanceManagement financeManagement,
					CommunityManagement communityManagement, BettingManagement bettingManagement, MessageManagement messageManagement){

		this.bettingManagement = bettingManagement;
		this.communityManagement = communityManagement;
		this.financeManagement = financeManagement;
		this.userManagement = userManagement;
		this.messageManagement = messageManagement;
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('BOSS')")
	String admin(){ return "admin";}

	@GetMapping("/admin/users")
	@PreAuthorize("hasRole('BOSS')")
	String allUsers(Model model) {

		model.addAttribute("users", userManagement.findAll());

		return "admin_allUsers";
	}

	@GetMapping("/admin/users/getInfo")
	@PreAuthorize("hasRole('BOSS')")
	String info(Model model, @RequestParam("id") Long id){
		User user = userManagement.findByUserId(id);
		if (user != null){
			model.addAttribute("user", user);
			model.addAttribute("communities",
					communityManagement.findPersonalCommunities(user.getUserAccount()));
			model.addAttribute("bets",
					bettingManagement.findBetsByUser(user.getUserAccount().getUsername()));
			return "admin_details";
		}
		return "redirect:/admin/users?error";
	}

	@GetMapping("/admin/communities")
	@PreAuthorize("hasRole('BOSS')")
	String allCommunities(Model model) {

		model.addAttribute("communities", communityManagement.findAll());

		return "admin_allCommunities";
	}

	@GetMapping("/admin/communities/getCommunityInfo")
	@PreAuthorize("hasRole('BOSS')")
	String communityInfo(Model model, @RequestParam("name") String name){
		Community community = communityManagement.findByCommunityName(name);
		if (community != null) {
			model.addAttribute("communityName", community.getName());
			model.addAttribute("usersInCommunity", findUsersByCommunityName(name));
			return "admin_communityDetails";
		}
		return "redirect:/admin/communities?error";
	}

	@GetMapping("/admin/bets")
	@PreAuthorize("hasRole('BOSS')")
	String allBets(Model model){
		model.addAttribute("money", bettingManagement.getMoneyFromAllBets());
		model.addAttribute("bets",bettingManagement.findNotEvaluatedBets());

		return "admin_allBets";
	}

	@GetMapping("/admin/evaluateBets")
	@PreAuthorize("hasRole('BOSS')")
	String allData(Model model){
		model.addAttribute("matches", bettingManagement.findDataByCategory(Category.FOOTBALL));
		model.addAttribute("lotteries", bettingManagement.findDataByCategory(Category.LOTTERY));

		return "admin_evaluatebet";
	}

	@PostMapping(path = "/admin/evaluateBets/football")
	@PreAuthorize("hasRole('BOSS')")
	String setFootball(@RequestParam("match") FootballMatch match,
					   @RequestParam("home_score") int homeScore, @RequestParam("guest_score") int guestScore){

		match.setResult(new Score(homeScore, guestScore));
		return evaluateBet(bettingManagement.findBetsByData(match), "Wettausschüttung zu " + match);
	}

	@PostMapping(path = "/admin/evaluateBets/lottery")
	@PreAuthorize("hasRole('BOSS')")
	String setLottery(@RequestParam("lottery") LotteryEntity lottery,
					  @RequestParam("numberStr") String numStr, @RequestParam("superNumber") int superNumber){

		lottery.setResult(new SelectNumber(numStr, superNumber));
		return evaluateBet(bettingManagement.findBetsByData(lottery), "Wettausschüttung zu "+lottery);
	}

	private String evaluateBet(Streamable<Bet> betsByData, String description){
		for(Bet bet : betsByData){
			Set<Map.Entry<String, Double>> payOut = bet.payOut().entrySet();
			for(Map.Entry<String, Double> entry : payOut){
				financeManagement.deposit(new FinanceForm(entry.getValue(), description),
						userManagement.findByUsername(entry.getKey()).getUserAccount());
			}
		}
		return "redirect:/admin";
	}

	public Set<User> findUsersByCommunityName(String name) {
		Streamable<User> allUsers = userManagement.findAll();
		Set<User> usersInCommunity = new HashSet<>();
		for(User user : allUsers){
			Set<Community> communities = communityManagement.findPersonalCommunities(user.getUserAccount());
			for (Community community : communities) {
				if (community.getName().equals(name)) {
					usersInCommunity.add(user);
				}
			}
		}
		return usersInCommunity;
	}
}
