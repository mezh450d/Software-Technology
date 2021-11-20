package lottery.user;

import javax.persistence.*;

import lottery.community.Community;
import lottery.betting.Bet;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

	private @Id @GeneratedValue long id;

	@OneToOne
	private UserAccount userAccount;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Community> communityList=new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	private List<Bet> betList=new ArrayList<>();

	public User(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public long getId() {
		return id;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void addCommunity(Community community){
		Assert.notNull(community,"Bitte mindestens ein Community eingeben!");
		communityList.add(community);
	}

	public void addBet(Bet bet){ betList.add(bet); }

	public List<Community> getCommunityList(){
		return communityList;
	}

	public List<Bet> getBetList() { return betList; }
}