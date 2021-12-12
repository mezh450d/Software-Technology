package lottery.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserEditForm {


	@NotEmpty(message = "{RegistrationForm.firstName.NotEmpty}")
	public final String firstName;

	@NotEmpty(message = "{RegistrationForm.lastName.NotEmpty}")
	public final String lastName;

	@NotEmpty(message = "{RegistrationForm.emailAddress.NotEmpty}")
	@Pattern(regexp = "^(.+)@(\\S+)$", message="{RegistrationForm.emailAddress.Pattern}")
	private final String emailAddress;

	@NotEmpty(message = "{RegistrationForm.lotteryAddress.NotEmpty}")
	@Pattern(regexp = "^[0-9]{10}$", message="{RegistrationForm.lotteryAddress.Pattern}")
	private String lotteryAddress;



	public UserEditForm(String firstName, String lastName, String emailAddress, String lotteryAddress) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.lotteryAddress = lotteryAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getLotteryAddress() {
		return lotteryAddress;
	}

}

