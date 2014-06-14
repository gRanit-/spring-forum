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
    public User getUserById(long userId) {
        return (User) this.sessionFactory.getCurrentSession().get(User.class, userId);
    }
    
    public User getUserByEmail(String email) {
        String query = "from User u where u.email = ?";
        return (User)this.sessionFactory.getCurrentSession().createQuery(query)
                .setParameter(0, email).list().get(0);
    }
    

    public void deleteUser(long userId) {
        User user = (User) this.sessionFactory.getCurrentSession().load(User.class, userId);
        if (user != null) {
            this.sessionFactory.getCurrentSession().delete(user);
        }
    }
    
    public void updateUserPassword(String email, String newPassword) {
        User user = getUserByEmail(email);
        if(user != null) {
            user.setPassword(newPassword);
            updateUser(user);
        }
    }
    
    public void updateUserNickname(String email, String newNickname) {
        User user = getUserByEmail(email);
        if(user != null) {
            user.setNick(newNickname);
            updateUser(user);
        }
    }
    
}
