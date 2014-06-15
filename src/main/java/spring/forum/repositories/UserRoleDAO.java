package spring.forum.repositories;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.forum.models.User;
import spring.forum.models.UserRole;

@Repository
public class UserRoleDAO {
	@Autowired
    private SessionFactory sessionFactory;
	
	
	
	public void addRole(UserRole role) {
		this.sessionFactory.getCurrentSession().save(role);
	}
	
	public void deleteRole(long roleId) {
		UserRole role = (UserRole) this.sessionFactory.getCurrentSession().load(UserRole.class, roleId);
        if (role != null) {
            this.sessionFactory.getCurrentSession().delete(role);
        }
    }
    
}
