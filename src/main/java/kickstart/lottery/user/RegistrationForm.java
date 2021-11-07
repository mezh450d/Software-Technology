package kickstart.lottery.user;

import javax.validation.constraints.NotEmpty;

public class RegistrationForm {

	//falls mehr Eingabedaten gewünscht werden, dann hier eintragen

	@NotEmpty(message = "{RegistrationForm.name.NotEmpty}")
	private final String name;

	@NotEmpty(message = "{RegistrationForm.password.NotEmpty}")
	private final String password;


	public RegistrationForm(String name, String password) {

		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

}
