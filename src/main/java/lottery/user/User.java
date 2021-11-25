package lottery.user;

import javax.persistence.*;

import org.salespointframework.useraccount.UserAccount;


@Entity
public class User {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name="user_id") long id;

	@OneToOne
	private UserAccount userAccount;

	@SuppressWarnings("unused")
	public User(){}

	public User(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public long getId() {
		return id;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

}