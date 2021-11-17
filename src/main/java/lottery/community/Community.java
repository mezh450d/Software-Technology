package lottery.community;

import lottery.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name= "Community")
public class Community {

	private @Id @GeneratedValue long id;

	private String name;

	private String password;

	//private Password.EncryptedPassword password;

	//private UserAccount communityAccount;

	@OneToMany(cascade = CascadeType.ALL)
	private List<User>users =new ArrayList<>();

	@SuppressWarnings("unused")
	public Community(){}

	public Community(String name, String password){
		this.name=name;
		this.password=password;
	}

//	public Community(UserAccount communityAccount) {
//		this.name=communityAccount.getUsername();
//		this.password=communityAccount.getPassword();
//
//	}

	public long getId() {
		return id;
	}

	//public UserAccount getCommunityAccount(){return communityAccount;}

	public List<User> getUsers() {
		return users;
	}


	public String getPassword(){
		return password;
	}

	public String getName(){
		return name;
	}

	public void addUsers(User user){

		if(user!=null){
			users.add(user);
		}

	}


}