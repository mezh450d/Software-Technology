package lottery.finance;

import lottery.user.RegistrationForm;
import lottery.user.User;
import lottery.user.UserManagement;
import lottery.user.UserRepository;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FinanceEntryTests {

	@Autowired
	UserManagement userManagement;

	@Mock
	UserAccount user;


	@Test
	void rejectsEmptyUser() {

		assertThatExceptionOfType(IllegalArgumentException.class)//
			.isThrownBy(() -> new FinanceEntry(null, 100.0 ,"Test", LocalDateTime.now()));
	}

	@Test
	void rejectsEmptyAmount() {

		assertThatExceptionOfType(IllegalArgumentException.class)//
				.isThrownBy(() -> new FinanceEntry(user, null ,"Test", LocalDateTime.now()));
	}

}