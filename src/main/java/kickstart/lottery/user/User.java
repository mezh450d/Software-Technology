package kickstart.lottery.user;

import javax.persistence.*;

import kickstart.community.Community;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
public class User {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name="user_id") long id;

	@OneToOne
	//@Column(name="user_userAccount")
	private UserAccount userAccount;

	@ManyToMany(mappedBy = "users")
	private Set<Community> communityList=new HashSet<>();

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

//	public void addCommunity(Community community){
//		Assert.notNull(community,"Bitte mindestens ein Community eingeben!");
//		communityList.add(community);
//	}


	public void setCommunityList(Set<Community> communityList) {
		this.communityList = communityList;
	}

	public Set<Community> getCommunityList(){
		return communityList;
	}
}