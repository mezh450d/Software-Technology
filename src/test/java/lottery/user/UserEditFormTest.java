package lottery.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserEditFormTest {

	UserEditForm form = new UserEditForm("Anna", "Bauer",
			"anna.1@gmail.com", "1234567890");

	@Test
	void testFirstName() {
		assertEquals("Anna", form.getFirstName());
	}

	@Test
	void testLastName() {
		assertEquals("Bauer", form.getLastName());
	}

	@Test
	void testEmailAddress() {
		assertEquals("anna.1@gmail.com", form.getEmailAddress());
	}

	@Test
	void testLotteryAddress() {
		assertEquals("1234567890", form.getLotteryAddress());
	}

}
