package kickstart.community;

import kickstart.lottery.user.User;
import kickstart.lottery.user.UserManagement;
import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.data.util.Streamable;
import org.springframework.util.Assert;

public class CommunityManagement {

	private final UserAccountManagement communityAccounts;

	private final CommunityRepository communities;

	CommunityManagement(UserAccountManagement communityAccounts, CommunityRepository communities){

		Assert.notNull(communities, "CommunityRepository must not be null!");
		Assert.notNull(communityAccounts, "CommunityAccountManagement must not be null!");

		this.communities=communities;
		this.communityAccounts=communityAccounts;
	}

	public Community createCommunity(RegistrationForm form){
		Assert.notNull(form, "Registration form must not be null!");

		var password = Password.UnencryptedPassword.of(form.getPassword());
		var userAccount = communityAccounts.create(form.getName(), password);

		return communities.save(new Community(userAccount));

	}

	public Community findCommunity(RegistrationForm form){

		Streamable<Community>communityliste=communities.findAll();
		for(Community community:communityliste){
			if(community.getName()==form.getName()) {
				if (community.getPassword().toString() == form.getPassword()) {
					return community;
				}
			}
		}return null;
	}


	public Streamable<Community> findAll() {
		return communities.findAll();
	}
}

