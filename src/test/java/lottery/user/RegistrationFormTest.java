package lottery.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationFormTest {

		@Autowired
		RegistrationForm registrationForm;

		@Test
		void testUsername() {
			RegistrationForm form = new RegistrationForm("Anna", "anna.1@gmail.com", "000000010", "123123");
			assertEquals(form.getName(), "Anna");
		}

		@Test
		void testPassword() {
			RegistrationForm form = new RegistrationForm("Anna", "anna.1@gmail.com", "000000010", "123123");
			assertEquals(form.getPassword(), "123123");
		}
}
