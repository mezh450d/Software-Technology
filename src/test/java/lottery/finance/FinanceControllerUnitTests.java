package lottery.finance;

import lottery.user.UserManagement;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

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
	FinanceManagement finances;

	@Test
	void populatesModelForFinance() {


		Model model = new ExtendedModelMap();
		FinanceForm form = new FinanceForm(100.0, "");
		finances.deposit(form, user1);
		FinanceController controller = new FinanceController(finances);
		String viewName = controller.finances(user1, model, new FinanceForm(100.0, null));
		assertThat(viewName).isEqualTo("finances");
//		assertThat(model.asMap().get("entries")).isInstanceOf(Iterable.class);
		assertThat(model.asMap().get("form")).isNotNull();
//		assertThat(model.getAttribute("balance")).isEqualTo(Money.of(100,"EUR"));
	}
}

