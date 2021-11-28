package lottery.admin;

import lottery.community.CommunityManagement;
import lottery.user.UserManagement;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

	private final UserManagement userManagement;
	private final CommunityManagement communityManagement;

	AdminController(UserManagement userManagement, CommunityManagement communityManagement) {
		this.userManagement = userManagement;
		this.communityManagement = communityManagement;
	}


	@GetMapping("/admin")
	String admin(Model model) {

		return "admin";
	}

	@GetMapping("/allusers")
	@PreAuthorize("hasRole('BOSS')")
	String alluser(Model model) {

		model.addAttribute("userInfo", userManagement.findAll().toList());

		return "allusers";
	}

	//
	@GetMapping("/admin/getList")
	@PreAuthorize("hasRole('BOSS')")
	String list(Model model, @RequestParam(value = "cid") String cname) {

//
//		if (cid == 1) {
//			LinkedList<UserInfo> info = new LinkedList<>();
//			for (int i = 1; i <= 2; i++) {
//				UserInfo userinfo = new UserInfo(i, "Jimmy",
//						25479, Money.of(4, EURO),
//						20, "TeamA : TeamB = 2:3",
//						47, "Win", "Lose");
//				info.add(userinfo);
//			}
//			model.addAttribute("info", info);
//		}
//		UserInfo userinfo = new UserInfo();
//		userinfo.setCommunityID(cid);
//		System.out.println(userinfo.getCommunityID());
//		model.addAttribute("cid", userinfo.getCommunityID());
		return "list";
	}

	@GetMapping("/admin/getInfo")
	@PreAuthorize("hasRole('BOSS')")
	String info(Model model, @RequestParam(value = "id") int id){
		UserInfo userInfo = new UserInfo(userManagement);
		AdminEntry adminEntry = new AdminEntry();
		if (userInfo.userExistOrNot(id) == 1){
			adminEntry.setId(id);
			System.out.println("the user id is " + id);
			model.addAttribute("id", adminEntry.getId());
			model.addAttribute("detail", userManagement.findByUserId(id));
			model.addAttribute("joinedCommunity", communityManagement.findPersonalCommunities(userManagement.findByUserId(id).getUserAccount()));
			return "details";
		}
		return "/admin";
	}
//
//	@GetMapping("/details")
//	@PreAuthorize("hasRole('BOSS')")
//	String details(Model model, @LoggedIn UserAccount user){
//		model.addAttribute("joinedCommunity", communityManagement.findPersonalCommunities(user));
//		return "details";
//	}


}