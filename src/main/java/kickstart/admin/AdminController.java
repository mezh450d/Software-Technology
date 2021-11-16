package kickstart.admin;

import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.LinkedList;

@Controller
public class AdminController {

	private final UserAccountManagement userAccountManagement;
	AdminController(UserAccountManagement userAccountManagement){
		this.userAccountManagement = userAccountManagement;
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

	@GetMapping("/admin")
	String admin(Model model){return "admin";}

//	@GetMapping("/list")
////	@PreAuthorize("hasRole('BOSS')")
//	String communityid(Model model){return "list";}

	@GetMapping("/allusers")
	@PreAuthorize("hasRole('BOSS')")
	String alluser(Model model){
//		LinkedList<Users> list = new LinkedList<>();
//		for(int i = 1; i <= 10; i++) {
//			Users entity = new Users(i, "user"+i);
//			list.add(entity);
//		}
//		System.out.println("I'm alluser.");
//		model.addAttribute("userinfo", list);
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
