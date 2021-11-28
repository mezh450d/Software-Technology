package lottery.admin;

import lottery.betting.Bet;
import lottery.betting.BettingManagement;
import lottery.betting.Category;
import lottery.betting.football.FootballMatch;
import lottery.betting.football.Score;
import lottery.betting.number.LotteryEntity;
import lottery.betting.number.SelectNumber;
import lottery.community.CommunityManagement;
import lottery.finance.FinanceForm;
import lottery.finance.FinanceManagement;
import lottery.user.UserManagement;
import org.springframework.data.util.Streamable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController2 {

	private final UserManagement userManagement;
	private final FinanceManagement financeManagement;
	private final CommunityManagement communityManagement;
	private final BettingManagement bettingManagement;

	AdminController2(UserManagement userManagement, FinanceManagement financeManagement,
					 CommunityManagement communityManagement, BettingManagement bettingManagement){

		this.bettingManagement = bettingManagement;
		this.communityManagement = communityManagement;
		this.financeManagement = financeManagement;
		this.userManagement = userManagement;
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('BOSS')")
	String admin(){return "admin2";}

	@GetMapping("/admin/users")
	@PreAuthorize("hasRole('BOSS')")
	String allUsers(){return "admin_allusers";}

	@GetMapping("/admin/evaluateBets")
	@PreAuthorize("hasRole('BOSS')")
	String allBets(Model model){
		model.addAttribute("matches", bettingManagement.findDataByCategory(Category.FOOTBALL));
		model.addAttribute("lotteries", bettingManagement.findDataByCategory(Category.LOTTERY));
		return "admin_evaluatebet";
	}

	@PostMapping(path = "/admin/evaluateBets/football")
	@PreAuthorize("hasRole('BOSS')")
	String setAndEvaluateFootball(@RequestParam("match") FootballMatch match,
						  @RequestParam("home_score") int homeScore, @RequestParam("guest_score") int guestScore) {

		match.setResult(new Score(homeScore, guestScore));
		Streamable<Bet> betsForData = bettingManagement.findBetsByData(match);
		for(Bet bet : betsForData){
			Double payOut = bet.payOut();
			financeManagement.deposit(new FinanceForm(payOut, "Wettausschüttung zu "+match),
					userManagement.findByUsername(bet.getUser()).getUserAccount());
		}
		return "redirect:/admin";
	}

	@PostMapping(path = "/admin/evaluateBets/lottery")
	@PreAuthorize("hasRole('BOSS')")
	String setAndEvaluateLottery(@RequestParam("lottery") LotteryEntity lottery,
						  @RequestParam("numberStr") String numStr, @RequestParam("superNumber") int superNumber) {

		lottery.setResult(new SelectNumber(numStr, superNumber));
		Streamable<Bet> betsForData = bettingManagement.findBetsByData(lottery);
		for(Bet bet : betsForData){
			Double payOut = bet.payOut();
			financeManagement.deposit(new FinanceForm(payOut, "Wettausschüttung zu "+lottery),
					userManagement.findByUsername(bet.getUser()).getUserAccount());
		}
		return "redirect:/admin";
	}

	@PostMapping("/list")
	String list(Model model){

		return  "list";
	}

//	@PostMapping("/admin")
//	String conmmunityid(Model model, Errors errors){
//		if(errors.hasErrors()){
//			return "redirect:/admin";
//		}
//		return "list";
//	}

//	@GetMapping("/list")
////	@PreAuthorize("hasRole('BOSS')")
//	String community(Model model){return "list";}

	@GetMapping("/allusers")
	@PreAuthorize("hasRole('BOSS')")
	String alluser(Model model){
//		LinkedList<Users> list = new LinkedList<>();
//		for(int i = 1; i <= 10; i++) {
//			Users entity = new Users(i, "user"+i);
//			list.add(entity);
//		}
//		model.addAttribute("userinfo", list);
		model.addAttribute("userList", userManagement.findAll());
		return "allusers";
	}

//	private final Community community;
//	private final FootballMatch footballMatch;
//	private final NumberLottery numberLottery;
//
//	AdminController(Community community, FootballMatch footballMatch, NumberLottery numberLottery){
//		this.community = community;
//		this.footballMatch = footballMatch;
//		this.numberLottery = numberLottery;
//	}
//
//	@GetMapping("/admin")
//	String information(Model model){
//		model.addAttribute("username", community.getName());
//		model.addAttribute("userid", community.getId());
//		model.addAttribute("footballbetting", );
//		model.addAttribute("numberslotterybetting", );
//		model.addAttribute("footballresults", footballMatch.getResult());
//		model.addAttribute("numberslotteryresults", numberLottery.getResult());
//
//		return "admin";
//	}
//
//	@PostMapping("/admin")
//	String financialsituation(Model model, @Valid FootballFinancialSituation footballsitatus){
//		model.addAttribute("footballfinancialsituation", footballsitatus.getResult(););
//		model.addAttribute("numberslotteryfinancialsituation",);
//
//		return "useraccount";
//	}
}
