package kickstart.community;

import kickstart.lottery.user.RegistrationForm;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.util.List;
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