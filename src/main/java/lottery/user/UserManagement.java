package lottery.user;

import org.salespointframework.useraccount.Password.UnencryptedPassword;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserManagement {

	public static final Role USER_ROLE = Role.of("USER");

	private final UserRepository users;
	private final UserAccountManagement userAccounts;

	UserManagement(UserRepository users, UserAccountManagement userAccounts) {

		Assert.notNull(users, "UserRepository must not be null!");
		Assert.notNull(userAccounts, "UserAccountManagement must not be null!");

		this.users = users;
		this.userAccounts = userAccounts;
	}


	//erstellt einen neuen User mit den übergebenen Daten
	public User createUser(RegistrationForm form) {

		Assert.notNull(form, "Registration form must not be null!");

		var password = UnencryptedPassword.of(form.getPassword());
		var emailAddress = form.getEmailAddress();
		var userAccount = userAccounts.create(form.getName(), password, USER_ROLE);
		userAccount.setEmail(emailAddress);

		var lotteryAddress = form.getLotteryAddress();

		return users.save(new User(userAccount, lotteryAddress));
	}


	public User findByUserAccount(UserAccount userAccount){
		System.out.println("NEED TO FIND USER WITH ID"+userAccount.getId());
		List<User> allUsers=users.findAll().toList();
		if(allUsers.isEmpty())System.out.println("userRepository ist empty!");
		for(User user:allUsers){
			System.out.println("this user'id ist"+user.getUserAccount().getId());
			if(user.getUserAccount().getId()==userAccount.getId()) return user;
		}
		return null;
	}

	public User findByUsername(String name){
		List<User> allUsers= users.findAll().toList();
		for(User user : allUsers){
			if(user.getUserAccount().getUsername().equals(name)) return user;
		}
		return null;
	}

	public void deleteUser(User user){ users.delete(user); }

	//Übergibt alle abgespeicherten Nutzer
	public Streamable<User> findAll() {
		return users.findAll();
	}
}
