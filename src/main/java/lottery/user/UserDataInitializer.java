package lottery.user;

import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.Password.UnencryptedPassword;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

@Component
@Order(10)
public class UserDataInitializer implements DataInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(UserDataInitializer.class);

	private final UserAccountManagement userAccountManagement;
	private final UserManagement userManagement;


	UserDataInitializer(UserAccountManagement userAccountManagement, UserManagement userManagement) {

		Assert.notNull(userAccountManagement, "UserAccountManagement must not be null!");
		Assert.notNull(userManagement, "UserRepository must not be null!");

		this.userAccountManagement = userAccountManagement;
		this.userManagement = userManagement;
	}

	@Override
	public void initialize() {

		// Falls die Datenbank schon Einträge enthält, überspringe die Initialisierung
		if (userAccountManagement.findByUsername("boss").isPresent()) {
			return;
		}

		LOG.info("Creating default users and customers.");

		userAccountManagement.create("boss", UnencryptedPassword.of("123"),  Role.of("BOSS"));

		var password = "123";

		List.of(//
				new RegistrationForm("hans", "hans@gmail.com", "0000000001", password, "00000"),
				new RegistrationForm("nick", "nick@gmail.com", "0000000002", password, "00000"),
				new RegistrationForm("jing", "jing@gmail.com", "0000000003", password, "00000"),
				new RegistrationForm("hannes", "hannes@gmail.com", "0000000004", password, "00000"),
				new RegistrationForm("ziyi", "ziyi@gmail.com", "0000000005", password, "00000"),
				new RegistrationForm("meng", "meng@gmail.com", "0000000006", password, "00000"),
				new RegistrationForm("shiyue", "shiyue@gmail.com", "0000000007", password, "00000"),
				new RegistrationForm("testUser", "testUser@gmail.com", "0000000008", password, "00000")
		).forEach(userManagement::createUser);
	}
}
