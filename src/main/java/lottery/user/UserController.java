package lottery.user;

import  javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

	private final UserManagement userManagement;

	UserController(UserManagement userManagement) {

		Assert.notNull(userManagement, "UserManagement must not be null!");

		this.userManagement = userManagement;
	}

	@PostMapping("/register")
	String registerNew(@Valid RegistrationForm form, Errors result) {

		//Übergebene Daten werden auf Richtigkeit überprüft
		if (result.hasErrors()) {
			return "register";
		}

		//Falls keine Fehler bei der Validierung aufgetreten sind, wird ein User mit den angegebenen Daten erstellt
		userManagement.createUser(form);

		return "redirect:/login";
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

}
