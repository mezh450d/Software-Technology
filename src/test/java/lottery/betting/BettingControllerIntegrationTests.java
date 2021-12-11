package lottery.betting;

import lottery.betting.bet.BetRepository;
import lottery.user.UserManagement;
import lottery.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;


import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.AssertEquals.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BettingControllerIntegrationTests {

	@Resource
	private BettingController bettingController;

	@Autowired
	UserManagement userManagement;

	@Autowired
	UserRepository userRepository;

	@Resource
	private BetRepository betRepository;

	@Mock
	UserAccount user;

	@Test
	void testToBetting() {
		String viewHome = bettingController.betting();
		assertThat(viewHome).isEqualTo("betting");
	}

	@Test
	void testToNumber() {
		Model model = new ExtendedModelMap();
		String viewHome = bettingController.number(user, model);
		assertThat(viewHome).isEqualTo("betting_number");
	}

	@Test
	void testToFootball() {
		Model model = new ExtendedModelMap();
		String viewHome = bettingController.football(user, model);
		assertThat(viewHome).isEqualTo("betting_football");
	}

//	@Test
//	void testFootball() {
//		FootballMatch footballMatch = new FootballMatch("testName", Money.of(1, EURO), LocalDateTime.now(), Category.FOOTBALL, "home", "guest");
//		String viewHome = bettingController.addBet(user, footballMatch, 10, 15, 1);
//		Streamable<Bet> byUser = betRepository.findByUser(user.getUsername());
//		List<Bet> bets = byUser.toList();
//		Bet bet = bets.get(bets.size() - 1);
//		String value = bet.getValue().toString();
//		assertEquals(value, "10 : 15", "value is error");
//		assertThat(viewHome).isEqualTo("redirect:/home");
//	}
//
//	@Test
//	void testLottery() {
//		LotteryEntity lotteryEntity = new LotteryEntity("name", Money.of(10, EURO), LocalDateTime.now(), Category.LOTTERY, "第一期");
//		bettingController.addBet(user, lotteryEntity, "1,2,3,4,5,6", 1, 10);
//		Streamable<Bet> byUser = betRepository.findByUser(user.getUsername());
//		List<Bet> bets = byUser.toList();
//		Bet bet = bets.get(bets.size() - 1);
//		String value = bet.getValue().toString();
//		assertEquals("SelectNumber: 1,2,3,4,5,6  SuperNumber: 1", value);
//	}


}
