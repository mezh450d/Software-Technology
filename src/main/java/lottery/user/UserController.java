package lottery.user;

import  javax.validation.Valid;

import org.springframework.data.util.Streamable;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;

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
