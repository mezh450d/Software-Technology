package lottery.community;

import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional
public class CommunityManagement {

	//private final UserAccountManagement communityAccounts;

	private final CommunityRepository communities;

//	private CommunityManagement(){
//		this.communityAccounts=null;
//		this.communities=null;
//	}

	public CommunityManagement(UserAccountManagement communityAccounts, CommunityRepository communities){

		Assert.notNull(communities, "CommunityRepository must not be null!");
		Assert.notNull(communityAccounts, "CommunityAccountManagement must not be null!");

		this.communities=communities;
		//this.communityAccounts=communityAccounts;
	}

	public Community createCommunity(CreateForm form){
		Assert.notNull(form, "Registration form must not be null!");

		//var password = Password.UnencryptedPassword.of(form.getPassword());
		//var userAccount = communityAccounts.create(form.getName(),password);

		return communities.save(new Community(form.getName(), form.getPassword()));

	}

	public void deleteCommunity(){
		communities.deleteAll();
	}

	public Community findCommunity(CreateForm form){

//		var password = Password.UnencryptedPassword.of(form.getPassword());
//		var userAccount = communityAccounts.create("123", password);
//		Community communityFind=new Community(userAccount);
//		List<Community>communityList=communities.findAll().toList();
//		for(Community community:communityList){
//
//			if(community.getName()==form.getName()&&community.getPassword()==communityFind.getPassword()){
//				communityAccounts.delete(userAccount);
//				return community;
//			}
//
//		}
//		communityAccounts.delete(userAccount);
//		return null;


		List<Community> communityList=communities.findAll().toList();

		for(Community community:communityList){

			if(community.getName().equals(form.getName())){
				if(community.getPassword().equals(form.getPassword())){
					return community;
				}
			}

		}return null;

	}

//	public Streamable<Community> findByCreateForm(CreateForm createForm, Sort sort){return communities.findByCreateForm(createForm,sort);}

	public Streamable<Community> findAll() {
		return communities.findAll();
	}
}

