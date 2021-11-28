package lottery.community;

import com.mysema.commons.lang.Assert;
import lottery.user.User;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.data.util.Streamable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.swing.*;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class CommunityController {

	private final CommunityManagement communityManagement;

	public CommunityController(CommunityManagement communityManagement){

		Assert.notNull(communityManagement, "CommunityManagement must not be null!");
		this.communityManagement=communityManagement;
	}

	@GetMapping("/community")
	public String community(@LoggedIn UserAccount user, Model model) {

		model.addAttribute("personalCommunities", communityManagement.findPersonalCommunities(user));
		model.addAttribute("joinableCommunities",communityManagement.findJoinableCommunities(user));

		return "community";
	}

	@GetMapping("/community/create")
	String create(Model model, CreateForm form) {
		return "community_create";
	}

	@PostMapping("/community/create")
	String createNew(@Valid CreateForm form, @LoggedIn UserAccount user, Errors result) {

		//Übergebene Daten werden auf Richtigkeit überprüft


		//Falls der angegebene Communityname schon vorhanden ist, keinen Community erstellen
		String communityName = form.getName();

		Streamable<Community> communities = communityManagement.findAll();

		for(Community community : communities){
			if(community.getName().equals(communityName)){
				return "redirect:/community/create?error";
			}
		}

		if (result.hasErrors()) {
			return "community_create";
		}

		communityManagement.createCommunity(form);
		Community community = communityManagement.findCommunityByForm(form);
		communityManagement.joinCommunity(community, user);

		return "redirect:/community";
	}

	@GetMapping("/community/join")
	String join(Model model, CreateForm form) {
		return "community_join";
	}

	@PostMapping("/community/join")
	String join(@Valid CreateForm form, @LoggedIn UserAccount user, Errors result){

		if(result.hasErrors()){
			return "community_join";
		}

		Community community= communityManagement.findCommunityByForm(form);
		if(community==null){
			return "community_join";
		}

		communityManagement.joinCommunity(community, user);

		return "redirect:/community";
	}

	@GetMapping("/communities")
	@PreAuthorize("hasRole('BOSS')")
	String communities(Model model) {

		model.addAttribute("communityList", communityManagement.findAll());

		return "communities";
	}
}
