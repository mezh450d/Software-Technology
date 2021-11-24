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
import java.util.*;

@Entity
@Table(name= "Communities")
public class Community {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name="community_id") long id;

	@Column(name="community_name")
	private String name;

	@Column(name="community_password")
	private String password;

	//private Password.EncryptedPassword password;

	//private UserAccount communityAccount;

	@ManyToMany(targetEntity = User.class,cascade = CascadeType.ALL)
	@JoinTable(name="Community_User",joinColumns = {@JoinColumn(name="Community_ID",referencedColumnName = "community_id")},inverseJoinColumns = {@JoinColumn(name="userID",referencedColumnName = "user_id")})
	private Set<User> users =new HashSet<>();

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

	public void setId(Long id){
		this.id=id;
	}

	//public UserAccount getCommunityAccount(){return communityAccount;}

	public Set<User> getUsers() {
		return users;
	}


	public String getPassword(){
		return password;
	}

	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}

	public void setUsers(Set<User>users){
		this.users=users;
	}
	public void addUsers(User user){

		if(user!=null){
			users.add(user);
		}

	}


}