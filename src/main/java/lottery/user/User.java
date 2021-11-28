package lottery.user;

import javax.persistence.*;

import org.salespointframework.useraccount.UserAccount;


@Entity
public class User {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name="user_id") long id;

	@OneToOne
	private UserAccount userAccount;

	private String lotteryAddress;

	@SuppressWarnings("unused")
	public User(){}

	public User(UserAccount userAccount, String lotteryAddress) {
		this.userAccount = userAccount;
		this.lotteryAddress = lotteryAddress;
	}

	public long getId() {
		return id;
	}

	public String getLotteryAddress(){ return lotteryAddress; }

	public UserAccount getUserAccount() {
		return userAccount;
	}

}