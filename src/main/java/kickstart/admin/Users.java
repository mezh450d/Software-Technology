package kickstart.admin;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Users {
	String name;

	private @Id @GeneratedValue int id;

	public Users(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}
}
