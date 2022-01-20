package lottery.partner;

import lottery.user.User;
import lottery.user.UserEditForm;
import lottery.user.UserManagement;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class PartnerController {

	private final UserManagement userManagement;

	PartnerController(UserManagement userManagement) {
		this.userManagement = userManagement;
	}

	@GetMapping(path = "/partner")
	public String partner(@LoggedIn UserAccount user, Model model, PartnerCodeForm form) {

		model.addAttribute("partnerCode", userManagement.findByUserAccount(user).getPartnerCode());
		//model.addAttribute("partners", userManagement.findByUserAccount(user).getPartners());
		User partner = userManagement.findByUsername(userManagement.findByUserAccount(user).getPartnerName());
		String partnerName = "";
		if(partner != null)
			partnerName = partner.getUserAccount().getUsername();

		model.addAttribute("partner", partnerName);
		model.addAttribute("hasPartnerCode", !userManagement.findByUserAccount(user).getPartnerCode().equals(""));
		model.addAttribute("newPartnerCode", "");
		model.addAttribute("form", form);

		return "partner";
	}

	@PostMapping("/partner")
	String createPartnerCode(@Valid @ModelAttribute("form") PartnerCodeForm form, Errors errors, Model model,
							 @RequestParam(value = "action") String action, BindingResult res, @LoggedIn UserAccount user) {

		String code = form.getNewPartnerCode();

		Streamable<User> users = userManagement.findAll();

		for(User u : users){
			if(u.getPartnerCode().equals(code)){
				ObjectError error = new ObjectError("globalError", "Dieser Code ist schon vergeben.");
				res.addError(error);
				model.addAttribute("hasPartnerCode", false);
				return "partner";
			}
		}

		if (errors.hasErrors()) {
			return "partner";
		}

		userManagement.createPartnerCode(user, form);
		return "redirect:/home";
	}
}
