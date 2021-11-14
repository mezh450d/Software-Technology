package kickstart.community;

import kickstart.lottery.user.User;
import net.bytebuddy.asm.Advice;
import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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