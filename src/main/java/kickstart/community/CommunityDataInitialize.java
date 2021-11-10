package kickstart.community;

import kickstart.lottery.user.RegistrationForm;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManagement;

import java.util.List;

public class CommunityDataInitialize  {

	private final UserAccountManagement communityManagement;

	private final CommunityRepository communityRepository;

	public CommunityDataInitialize(UserAccountManagement communityManagement, CommunityRepository communityRepository){

		this.communityRepository=communityRepository;
		this.communityManagement=communityManagement;
	}

	public void initialize() {}
}