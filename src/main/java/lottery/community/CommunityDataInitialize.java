package lottery.community;

import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.stereotype.Component;

@Component
public class CommunityDataInitialize implements DataInitializer  {

	private final UserAccountManagement communityManagement;

	private final CommunityRepository communityRepository;

//	private CommunityDataInitialize(){
//		this.communityManagement=null;
//		this.communityRepository=null;
//	}
	CommunityDataInitialize(UserAccountManagement communityManagement, CommunityRepository communityRepository){

		this.communityRepository=communityRepository;
		this.communityManagement=communityManagement;
	}

	public void initialize() {}
}