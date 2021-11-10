package kickstart.community;

import kickstart.lottery.user.User;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

public class CommunityController {

	private final CommunityManagement communityManagement;

	public CommunityController(CommunityManagement communityManagement){

		Assert.notNull(communityManagement, "CommunityManagement must not be null!");
		this.communityManagement=communityManagement;

	}

	@PostMapping("/create")
	String createNew(@Valid RegistrationForm form, Errors result) {


		if (result.hasErrors()) {
			return "create";
		}

		communityManagement.createCommunity(form);

		return "redirect:/community";
	}

	@GetMapping("/create")
	String create(Model model, RegistrationForm form) {
		return "create";
	}

	@GetMapping("/communities")
	@PreAuthorize("hasRole('BOSS')")
	String communities(Model model) {

		model.addAttribute("communityList", communityManagement.findAll());

		return "communities";
	}

	@GetMapping("/community")
	public String community(@LoggedIn User user, Model model) {
		model.addAttribute("communityList",user.getCommunityList());
		return "community";
	}

	@PostMapping("/join")
	String join(@Valid RegistrationForm form, @LoggedIn User user, Errors result){
		if(result.hasErrors()){
			return "join";
		}
		Community community=communityManagement.findCommunity(form);
		if(community==null){
			return "join";
		}
		community.addUsers(user);
		user.addCommunity(community);
		return "community";
	}

	@GetMapping("/join")
	String join(Model model, RegistrationForm form) {
		return "join";
	}
}
