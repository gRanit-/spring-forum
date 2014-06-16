package spring.forum.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import spring.forum.models.User;
import spring.forum.models.UserRole;
import spring.forum.repositories.UserRoleDAO;

public class UserRoleManager {
	
	private UserRoleDAO userRoleDAO;
	@Autowired
	UserRoleManager(UserRoleDAO userRoleDAO){
		
		this.userRoleDAO=userRoleDAO;
	}
	   public UserRoleManager() {
	    }

	@Transactional
    public void addUserRole(UserRole userRole) {
        userRoleDAO.addRole(userRole);
    }

}
