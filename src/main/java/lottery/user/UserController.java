package lottery.user;

import  javax.validation.Valid;

import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.data.util.Streamable;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

	private final UserManagement userManagement;
	private boolean userExists = false;

	UserController(UserManagement userManagement) {

		Assert.notNull(userManagement, "UserManagement must not be null!");

		this.userManagement = userManagement;
	}

	@PostMapping("/register")
	String registerNew(@Valid RegistrationForm form, BindingResult res, Errors result, Model model) {

		//Übergebene Daten werden auf Richtigkeit überprüft


		//Falls der angegebene Nutzername schon vorhanden ist, keinen User erstellen
		String username = form.getName();

		Streamable<User> users = userManagement.findAll();

		if(username.equals("boss")){
			ObjectError error = new ObjectError("globalError", "Sie können sich nicht als Boss registrieren.");
			res.addError(error);
			return "register";
		}

		for(User user : users){
			if(user.getUserAccount().getUsername().equals(username)){
				ObjectError error = new ObjectError("globalError", "Dieser Nutzername ist schon vergeben.");
				res.addError(error);
				return "register";
			}
		}

		//Falls das Formular Fehler enthält, keinen User erstellen
		if (result.hasErrors()) {
			return "register";
		}

		//Falls keine Fehler bei der Validierung aufgetreten sind, wird ein User mit den angegebenen Daten erstellt
		userManagement.createUser(form);
		return "redirect:/login";
	}

	@PostMapping("/edit_user")
	String editCurrentUser(@Valid @ModelAttribute("form") UserEditForm form, Errors errors, Model model,
						   @RequestParam(value = "action") String action, @LoggedIn UserAccount user) {


		//Falls das Formular Fehler enthält, keinen User erstellen
		if (errors.hasErrors()) {
			return edit_user(user, model, form);
		}

		userManagement.editUser(user, form);
		return "redirect:/home";
	}


	@GetMapping("/register")
	String register(Model model, RegistrationForm form) {
		return "register";
	}

	@GetMapping("/")
	public String index() {
		return "redirect:login";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/edit_user")
	public String edit_user(@LoggedIn UserAccount user, Model model, UserEditForm form) {

		model.addAttribute("firstName", user.getFirstname());
		model.addAttribute("lastName", user.getLastname());
		model.addAttribute("emailAddress", user.getEmail());
		model.addAttribute("lotteryAddress", userManagement.findByUserAccount(user).getLotteryAddress());
		model.addAttribute("form", form);
		return "edit_user";
	}


}
