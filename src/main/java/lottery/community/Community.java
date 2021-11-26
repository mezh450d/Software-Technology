package lottery.community;

import org.salespointframework.useraccount.UserAccount;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Community {

	private @Id @GeneratedValue long id;

	private String name;

	private String password;

	@ManyToMany(cascade = CascadeType.ALL)
	private final Set<UserAccount> users = new HashSet<>();

	@SuppressWarnings("unused")
	public Community(){}

	public Community(String name, String password){
		this.name=name;
		this.password=password;
	}

	public long getId() { return id; }

	public String getName(){ return name; }

	public String getPassword(){ return password; }

	public Set<UserAccount> getUsers() { return users; }


	public void addUser(UserAccount user){
		if(user!=null){
			users.add(user);
		}
	}

	public boolean userInCommunity(UserAccount user){
		if(user!=null) return users.contains(user);
		return false;
	}

}