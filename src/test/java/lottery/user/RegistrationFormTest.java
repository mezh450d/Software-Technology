package lottery.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationFormTest {

		@Autowired
		RegistrationForm registrationForm;

		@Test
		void testUsername() {
			RegistrationForm form = new RegistrationForm("Anna", "anna.1@gmail.com", "000000010", "123123","00000");
			assertEquals("Anna", form.getName());
		}

		@Test
		void testPassword() {
			RegistrationForm form = new RegistrationForm("Anna", "anna.1@gmail.com", "000000010", "123123","00000");
			assertEquals("123123", form.getPassword());
		}
}
