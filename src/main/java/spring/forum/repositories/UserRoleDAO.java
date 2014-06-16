package spring.forum.repositories;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.forum.models.User;
import spring.forum.models.UserRole;

@Repository
public class UserRoleDAO {
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Autowired
    private SessionFactory sessionFactory;
	
	
	
	public void addRole(UserRole role) {
		this.sessionFactory.getCurrentSession().save(role);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserRole> getRole(User user){
		String query = "from UserRole u where u.user_id = ?";
		return this.sessionFactory.getCurrentSession().createQuery(query)
                .setParameter(0, user.getId()).list();
	}
	public void deleteRole(long roleId) {
		UserRole role = (UserRole) this.sessionFactory.getCurrentSession().load(UserRole.class, roleId);
        if (role != null) {
            this.sessionFactory.getCurrentSession().delete(role);
        }
    }
    
}
