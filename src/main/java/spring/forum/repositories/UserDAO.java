package spring.forum.repositories;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.forum.models.User;

@Repository
public class UserDAO {
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Autowired
	private SessionFactory sessionFactory;

	public void addUser(User user) {
		this.sessionFactory.getCurrentSession().save(user);
	}
	
    public List<User> getAllUsers() {
        return this.sessionFactory.getCurrentSession().createQuery("from User").list();
    }
    
    public User getUserWithEmail(String email) {
    	return (User) this.sessionFactory.getCurrentSession().createQuery("from User u Where u.email="+email);
    	
    }
    
    public void updateUser(User user) {
    	this.sessionFactory.getCurrentSession().update(user);
    }
    public User getUserById(int userId) {
        return (User) this.sessionFactory.getCurrentSession().get(User.class, userId);
    }
    
    public User getUserByEmail(String email) {
        String query = "from User u where u.email = ?";
        return (User)this.sessionFactory.getCurrentSession().createQuery(query)
                .setParameter(0, email).list().get(0);
    }
    
    
    
}
