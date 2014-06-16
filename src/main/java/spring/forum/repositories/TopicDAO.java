package spring.forum.repositories;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import spring.forum.models.Post;
import spring.forum.models.Topic;
import spring.forum.models.User;

public class TopicDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addTopic(Topic topic) {
		this.sessionFactory.getCurrentSession().save(topic);
	}

	public Topic getTopicByID(long id){
		 return (Topic) this.sessionFactory.getCurrentSession().get(Topic.class, id);
	}
	public List<Topic> getAllTopics() {
		// TODO users vs user
		return this.sessionFactory.getCurrentSession()
				.createQuery("from Topic").list();
	}
	
	public List<Post> getAllTopicsForUser(User user){
		return this.sessionFactory.getCurrentSession()
				.createQuery("from Topic p where p.author="+user.getId()).list();
	}
	
	
	public void deleteTopic(long topiId) {
		Topic topic = (Topic) this.sessionFactory.getCurrentSession().load(
				Topic.class, topiId);
		if (topic != null) {
			this.sessionFactory.getCurrentSession().delete(topic);
		}
	}

}
