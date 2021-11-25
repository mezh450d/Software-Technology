package lottery.finance;

import lottery.user.RegistrationForm;
import lottery.user.User;
import lottery.user.UserManagement;
import lottery.user.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Streamable;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FinanceEntryTests {

	@Autowired
	UserManagement userManagement;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserAccount user1, user2;


	@BeforeAll
	void before() {
		userManagement.createUser(new RegistrationForm("user1", "123"));
		userManagement.createUser(new RegistrationForm("user2", "456"));
		Streamable<User> users = userRepository.findAll();
		List<User> userList = users.toList();
		user1 = userList.get(0).getUserAccount();
		user2 = userList.get(1).getUserAccount();
	}

	@Test
	void rejectsEmptyUser() {

		assertThatExceptionOfType(IllegalArgumentException.class)//
			.isThrownBy(() -> new FinanceEntry(null, 100.0 ,"EUR", LocalDateTime.now()));
	}

	@Test
	void rejectsEmptyAmount() {

		assertThatExceptionOfType(IllegalArgumentException.class)//
				.isThrownBy(() -> new FinanceEntry(user1, null ,"EUR", LocalDateTime.now()));
	}

	@Test
	void rejectsNegativAmount() {

		assertThatExceptionOfType(IllegalArgumentException.class).
				isThrownBy(() -> new FinanceEntry(user1, -100.0 ,"EUR", LocalDateTime.now()));
	}

}