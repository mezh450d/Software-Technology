package kickstart.community;

import com.mysema.commons.lang.Assert;
import kickstart.lottery.user.User;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Streamable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import javax.xml.transform.Result;
import java.util.List;
import java.util.concurrent.Callable;

@Controller
public class CommunityController {

	private final CommunityManagement communityManagement;

//	@SuppressWarnings("unused")
//	private CommunityController(){this.communityManagement=null;}

	CommunityController(CommunityManagement communityManagement){

		Assert.notNull(communityManagement, "CommunityManagement must not be null!");
		this.communityManagement=communityManagement;

	}

	@PostMapping("/create")
	String createNew(@Valid CreateForm form, @LoggedIn User user, Errors result) {


		if (result.hasErrors()) {
			return "create";
		}

		communityManagement.createCommunity(form);

		return "redirect:/community";
	}

	@GetMapping("/create")
	String create(Model model, CreateForm form) {
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
		model.addAttribute("communitiesall",communityManagement.findAll());
		model.addAttribute("communityList",user.getCommunityList());
		System.out.println(user.getId());
		if(user.getCommunityList().isEmpty())System.out.println("hier ist empty");
		return "community";
	}

	@PostMapping("/join")
	String join(@Valid CreateForm form, @LoggedIn User user, Errors result){
		if(result.hasErrors()){
			return "join";
		}
		Community community=communityManagement.findCommunity(form);
		System.out.println(user.getId());

		if(community==null){
			return "join";
		}
			community.addUsers(user);
			user.addCommunity(community);
			List<User>users=community.getUsers();
			if(users.isEmpty())  System.out.println("usersemp");
			List<Community>communitya=user.getCommunityList();
		    if(communitya.isEmpty())  System.out.println("communityemp");


		return "redirect:/community";
	}

	@GetMapping("/join")
	String join(Model model, CreateForm form) {
		return "join";
	}
}
