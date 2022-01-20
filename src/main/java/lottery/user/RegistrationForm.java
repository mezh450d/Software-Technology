package lottery.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class RegistrationForm {

	//falls mehr Eingabedaten gewünscht werden, dann hier eintragen

	@NotEmpty(message = "{RegistrationForm.name.NotEmpty}")
	private final String name;

	@NotEmpty(message = "{RegistrationForm.emailAddress.NotEmpty}")
	@Pattern(regexp = "^(.+)@(\\S+)$", message="{RegistrationForm.emailAddress.Pattern}")
	private final String emailAddress;

	@NotEmpty(message = "{RegistrationForm.lotteryAddress.NotEmpty}")
	@Pattern(regexp = "^[0-9]{10}$", message="{RegistrationForm.lotteryAddress.Pattern}")
	private final String lotteryAddress;

	@NotEmpty(message = "{RegistrationForm.password.NotEmpty}")
	private final String password;

	private final String partnerCode;


	public RegistrationForm(String name, String emailAddress, String lotteryAddress, String password, String partnerCode) {

		this.name = name;
		this.emailAddress = emailAddress;
		this.lotteryAddress = lotteryAddress;
		this.password = password;
		this.partnerCode = partnerCode;
	}

	public String getName() {
		return name;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getLotteryAddress() {
		return lotteryAddress;
	}

	public String getPassword() {
		return password;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

}

