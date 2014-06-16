package spring.forum.repositories;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.forum.models.Post;
import spring.forum.models.User;

@Repository
public class PostDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	public void addPost(Post post) {
		this.sessionFactory.getCurrentSession().save(post);
	}
	
	public List<Post> getAllPosts(){
		return this.sessionFactory.getCurrentSession().createQuery("from Post").list();
	}
	
	
	
	
}
