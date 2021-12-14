package lottery.home;

import lottery.betting.BettingManagement;
import lottery.finance.FinanceManagement;
import lottery.home.message.MessageManagement;
import lottery.user.User;
import lottery.user.UserManagement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import javax.annotation.Resource;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HomeControllerIntegrationTests {
	@Resource
	HomeController homeController;

	@Autowired
	UserManagement userManagement;

	User user;

	@BeforeAll
	void before(){
		user = userManagement.findByUsername("testUser");
	}

	@Test
	void testHome() {
		Model model = new ExtendedModelMap();
		String viewHome = homeController.home(user.getUserAccount(), model);
		assertThat(viewHome).isEqualTo("home");
	}

	@Test
	void testMessage() {
		Model model = new ExtendedModelMap();
		String viewHome = homeController.message(user.getUserAccount(), model);
		assertThat(viewHome).isEqualTo("message");
	}
}
