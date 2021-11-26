package lottery.finance;

import lottery.user.RegistrationForm;
import lottery.user.User;
import lottery.user.UserManagement;
import lottery.user.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.QUserAccount;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Streamable;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FinanceEntryTests {

	@Autowired
	UserManagement userManagement;

	UserAccount user1;


	@BeforeAll
	void before() {
		user1 = userManagement.findByUsername("testUser").getUserAccount();
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

//	@Test
//	void rejectsNegativAmount() {
//
//		assertThatExceptionOfType(IllegalArgumentException.class).
//				isThrownBy(() -> new FinanceEntry(user1, -100.0 ,"EUR", LocalDateTime.now()));
//	}

}