package lottery.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationFormTest {

		RegistrationForm form = new RegistrationForm("Anna", "anna.1@gmail.com",
				"000000010", "123123","00000");;

		@Test
		void testUsername() {
			assertEquals("Anna", form.getName());
		}

		@Test
		void testPassword() {
			assertEquals("123123", form.getPassword());
		}
}
