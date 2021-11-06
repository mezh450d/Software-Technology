package kickstart.lottery.user;

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

	/**
	 * Creates a new {@link UserDataInitializer} with the given {@link UserAccountManagement} and
	 * {@link UserRepository}.
	 *
	 * @param userAccountManagement must not be {@literal null}.
	 * @param userManagement must not be {@literal null}.
	 */
	UserDataInitializer(UserAccountManagement userAccountManagement, UserManagement userManagement) {

		Assert.notNull(userAccountManagement, "UserAccountManagement must not be null!");
		Assert.notNull(userManagement, "UserRepository must not be null!");

		this.userAccountManagement = userAccountManagement;
		this.userManagement = userManagement;
	}

	@Override
	public void initialize() {

		// Skip creation if database was already populated
		if (userAccountManagement.findByUsername("boss").isPresent()) {
			return;
		}

		LOG.info("Creating default users and customers.");

		userAccountManagement.create("boss", UnencryptedPassword.of("123"),  Role.of("BOSS"));

		var password = "123";

		List.of(//
				new RegistrationForm("hans", password),
				new RegistrationForm("dextermorgan", password),
				new RegistrationForm("earlhickey", password),
				new RegistrationForm("mclovinfogell", password)
		).forEach(userManagement::createUser);
	}
}
