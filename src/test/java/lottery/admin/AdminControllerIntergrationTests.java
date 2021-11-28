package lottery.admin;
import static org.assertj.core.api.Assertions.*;

import lottery.AbstractIntegrationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.ExtendedModelMap;


class AdminControllerIntergrationTests extends AbstractIntegrationTests {
	@Autowired
	AdminController2 controller;

	/**
	 * Does not use any authentication and should raise a security exception.
	 */
	@Test
	void rejectsUnauthenticatedAccessToController() {

		assertThatExceptionOfType(AuthenticationException.class) //
				.isThrownBy(() -> controller.alluser(new ExtendedModelMap()));
	}

	/**
	 * Uses {@link WithMockUser} to simulate access by a user with boss role.
	 */
	@Test
	@WithMockUser(roles = "BOSS")
	void allowsAuthenticatedAccessToController() {

		ExtendedModelMap model = new ExtendedModelMap();

		controller.alluser(model);

		assertThat(model.get("userList")).isNotNull();
	}
}
