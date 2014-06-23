package spring.forum.repositories;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.forum.models.Post;
import spring.forum.models.Topic;
import spring.forum.models.User;

@Repository
public class PostDAO implements Serializable {

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

	
	public List<Post> getAllPosts() {
		// TODO users vs user
		return this.sessionFactory.getCurrentSession()
				.createQuery("from Post").list();
	}
	
	public List<Post> getAllPostsForUser(User user){
		return this.sessionFactory.getCurrentSession()
				.createQuery("from Post p where p.author="+user.getId()).list();
	}
	
	public List<Post> getAllPostsForTopic(Topic topic){
		return this.sessionFactory.getCurrentSession()
				.createQuery("from Post p where p.topic="+topic.getId()).list();
	}
//	public User getAuthor(){
//		
//	}
	
	public void deletePost(long userId) {
		Post post = (Post) this.sessionFactory.getCurrentSession().load(
				Post.class, userId);
		if (post != null) {
			this.sessionFactory.getCurrentSession().delete(post);
		}
	}

}
