package lottery.finance;

import lottery.betting.BetRepository;
import lottery.user.RegistrationForm;
import lottery.user.User;
import lottery.user.UserManagement;
import lottery.user.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.assertj.core.api.Assertions.*;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Streamable;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class FinanceControllerUnitTests {

	@Autowired
	UserManagement userManagement;

	UserAccount user1;


	@BeforeAll
	void before() {
		user1 = userManagement.findByUsername("testUser").getUserAccount();
	}

	@Mock
	FinanceRepository finances;

	@Mock
	BetRepository bets;

	@Test
	void populatesModelForFinance() {


		Model model = new ExtendedModelMap();
		FinanceEntry entry = new FinanceEntry(user1, 100.0, "", LocalDateTime.now(), Money.of(0.0,"EUR"));
		finances.save(entry);
		FinanceController controller = new FinanceController(finances, bets);
		String viewName = controller.financesOverview(user1, model, new FinanceForm(100.0, null));
		assertThat(viewName).isEqualTo("finances");
//		assertThat(model.asMap().get("entries")).isInstanceOf(Iterable.class);
		assertThat(model.asMap().get("form")).isNotNull();
//		assertThat(model.getAttribute("balance")).isEqualTo(Money.of(100,"EUR"));
	}
}

