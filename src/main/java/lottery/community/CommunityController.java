package lottery.community;

import com.mysema.commons.lang.Assert;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.data.util.Streamable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class CommunityController {

	private final CommunityManagement management;

	public CommunityController(CommunityManagement management){

		Assert.notNull(management, "CommunityManagement must not be null!");
		this.management = management;
	}

	@GetMapping("/community")
	public String community(@LoggedIn UserAccount user, Model model) {

		model.addAttribute("personalCommunities", management.findPersonalCommunities(user));
		model.addAttribute("joinableCommunities", management.findJoinableCommunities(user));

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

		Streamable<Community> communities = management.findAll();

		for(Community community : communities){
			if(community.getName().equals(communityName)){
				return "redirect:/community/create?error";
			}
		}

		if (result.hasErrors()) {
			return "community_create";
		}

		management.createCommunity(form);
		Community community = management.findCommunityByForm(form);
		management.joinCommunity(community, user);

		return "redirect:/community";
	}

	@GetMapping("/community/join")
	String join(Model model, CreateForm form, String communityName) {
		model.addAttribute("communityName", communityName);
		return "community_join";
	}

	@PostMapping("/community/join")
	String join(@Valid CreateForm form, @LoggedIn UserAccount user, BindingResult res,Errors result){

		if(result.hasErrors()){
			return "community_join";
		}

		Community community= management.findCommunityByForm(form);
		if(community==null){
			ObjectError error = new ObjectError("globalError", "Fehlerhafte Communitydaten.");
			res.addError(error);
			return "community_join";
		}

		management.joinCommunity(community, user);

		return "redirect:/community";
	}

	@GetMapping("/communities")
	@PreAuthorize("hasRole('BOSS')")
	String communities(Model model) {

		model.addAttribute("communityList", management.findAll());

		return "communities";
	}
}
