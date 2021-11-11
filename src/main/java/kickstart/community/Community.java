package kickstart.community;

import kickstart.lottery.user.User;
import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.UserAccount;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Entity
//@Table(name= "Community")
public class Community {

	private @Id @GeneratedValue long id;

	private String name;

	private Password.EncryptedPassword password;

	@OneToMany(cascade = CascadeType.ALL)
	private List<User>users =new ArrayList<>();

	public Community(){}

	public Community(String name, org.salespointframework.useraccount.Password.EncryptedPassword password){
		this.name=name;
		this.password=password;
	}

	public Community(UserAccount communityAccount) {
		this.name=communityAccount.getUsername();
		this.password=communityAccount.getPassword();
	}

	public long getId() {
		return id;
	}

	public List<User> getUsers() {
		return users;
	}

	public Password.EncryptedPassword getPassword(){
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