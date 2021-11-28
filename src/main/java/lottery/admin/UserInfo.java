package lottery.admin;

import lottery.user.User;
import lottery.user.UserManagement;

import java.util.List;


public class UserInfo {

	private final UserManagement userManagement;
	private int idExist = 0;


	public UserInfo(UserManagement userManagement){
		this.userManagement = userManagement;
	}

	public int userExistOrNot(int id){
		List<User> allUserId = userManagement.findAll().toList();
		for (User user : allUserId) {
			if (user.getId() == id){
				idExist = 1;
			}
		}
		System.out.println("idExist is " + idExist);
		return idExist;
	}
}
