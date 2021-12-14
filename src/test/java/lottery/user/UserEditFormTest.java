package lottery.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserEditFormTest {

	@Autowired
	UserEditForm userEditForm;

	@Test
	void testFirstName() {
		UserEditForm form = new UserEditForm("Anna", "Bauer", "anna.1@gmail.com", "1234567890");
		assertEquals("Anna", form.getFirstName());
	}

	@Test
	void testLastName() {
		UserEditForm form = new UserEditForm("Anna", "Bauer", "anna.1@gmail.com", "1234567890");
		assertEquals("Bauer", form.getLastName());
	}

	@Test
	void testEmailAddress() {
		UserEditForm form = new UserEditForm("Anna", "Bauer", "anna.1@gmail.com", "1234567890");
		assertEquals("anna.1@gmail.com", form.getEmailAddress());
	}

	@Test
	void testLotteryAddress() {
		UserEditForm form = new UserEditForm("Anna", "Bauer", "anna.1@gmail.com", "1234567890");
		assertEquals("1234567890", form.getLotteryAddress());
	}

}
