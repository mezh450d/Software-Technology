package kickstart.community;

import com.mysema.commons.lang.Assert;
import kickstart.lottery.user.User;
import kickstart.lottery.user.UserManagement;
import kickstart.lottery.user.UserRepository;
import org.salespointframework.useraccount.UserAccount;
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
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;

@Controller
public class CommunityController {

	private final CommunityManagement communityManagement;
	private final UserRepository userRepository;
	private final UserManagement userManagement;
	private final CommunityRepository communityRepository;

//	@SuppressWarnings("unused")
//	private CommunityController(){this.communityManagement=null;}

	CommunityController(CommunityManagement communityManagement,UserRepository userRepository,CommunityRepository communityRepository,UserManagement userManagement){

		Assert.notNull(communityManagement, "CommunityManagement must not be null!");
		this.communityManagement=communityManagement;
		this.communityRepository=communityRepository;
		this.userRepository=userRepository;
		this.userManagement=userManagement;
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
	String join(@Valid CreateForm form, @LoggedIn Optional<UserAccount>  user, Errors result){
		if(result.hasErrors()){
			return "join";
		}
		if(!user.isPresent())System.out.println("user is not present");
		if(user.isEmpty())System.out.println("user is empty");
		Community community=communityManagement.findCommunity(form);
		if(community==null)System.out.println("cannot find community");

		if(community==null){
			return "join";
		}
			UserAccount userAccount=user.get();
			User findUser=userManagement.findByUserAccount(userAccount);
			if(findUser==null)System.out.println("cannot find user");
			community.getUsers().add(findUser);
			communityRepository.save(community);

//			List<UserAccount>users=community.getUsers();
//			if(users.isEmpty())  System.out.println("usersemp");
//			List<Community>communitya=findUser.getCommunityList();
//		    if(communitya.isEmpty())  System.out.println("communityemp");


		return "redirect:/community";
	}

	@GetMapping("/join")
	String join(Model model, CreateForm form) {
		return "join";
	}
}
