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
		assertEquals(form.getFirstName(), "Anna");
	}

	@Test
	void testLastName() {
		UserEditForm form = new UserEditForm("Anna", "Bauer", "anna.1@gmail.com", "1234567890");
		assertEquals(form.getLastName(), "Bauer");
	}

	@Test
	void testEmailAddress() {
		UserEditForm form = new UserEditForm("Anna", "Bauer", "anna.1@gmail.com", "1234567890");
		assertEquals(form.getEmailAddress(), "anna.1@gmail.com");
	}

	@Test
	void testLotteryAddress() {
		UserEditForm form = new UserEditForm("Anna", "Bauer", "anna.1@gmail.com", "1234567890");
		assertEquals(form.getLotteryAddress(), "1234567890");
	}

}
