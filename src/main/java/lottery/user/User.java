package lottery.user;

import javax.persistence.*;

import org.salespointframework.useraccount.UserAccount;


@Entity
public class User {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

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

	public void setLotteryAddress(String lottery_address){
		lotteryAddress = lottery_address;
	}
}