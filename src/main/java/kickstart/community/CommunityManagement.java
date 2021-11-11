package kickstart.community;

import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.data.util.Streamable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class CommunityManagement {

	private final UserAccountManagement communityAccounts;

	private final CommunityRepository communities;

	public CommunityManagement(UserAccountManagement communityAccounts, CommunityRepository communities){

		Assert.notNull(communities, "CommunityRepository must not be null!");
		Assert.notNull(communityAccounts, "CommunityAccountManagement must not be null!");

		this.communities=communities;
		this.communityAccounts=communityAccounts;
	}

	public Community createCommunity(CreateForm form){
		Assert.notNull(form, "Registration form must not be null!");

		var password = Password.UnencryptedPassword.of(form.getPassword());
		var userAccount = communityAccounts.create(form.getName(), password);

		return communities.save(new Community(userAccount));

	}

	public Community findCommunity(CreateForm form){

		Streamable<Community>communityliste=communities.findAll();
		var password = Password.UnencryptedPassword.of(form.getPassword());
		for(Community community:communityliste){
			if(community.getName().equals(form.getName())) {
				if (community.getPassword().equals(password) ) {
					return community;
				}
			}
		}return null;
	}


	public Streamable<Community> findAll() {
		return communities.findAll();
	}
}

