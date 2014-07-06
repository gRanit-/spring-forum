package spring.forum.repositories;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import spring.forum.models.Post;
import spring.forum.models.Topic;
import spring.forum.models.User;

@Repository
public class TopicDAO implements Serializable {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private MemcachedClient memcachedClient;
	
	
	
	public void addTopic(Topic topic) {
		this.sessionFactory.getCurrentSession().save(topic);

		List<Topic> topics = null;

		topics = (List<Topic>) this.sessionFactory.getCurrentSession()
				.createQuery("from Topic").list();
		
		memcachedClient.delete("topics");
		memcachedClient.set("topics", 0, topics);
	}


	public Topic getTopicByID(long id) {
		List<Topic> topics = null;
		Topic topic = null;
		
		topics = (List<Topic>) memcachedClient.get("topics");
		if (topics == null) {
			System.out.println("Couldn't get topic from memcached");
			topics = (List<Topic>) this.sessionFactory.getCurrentSession()
					.createQuery("from Topic").list();
			memcachedClient.set("topics", 0, topics);

		}

		for (Topic t : topics)
			if (t.getId() == id) {
				topic = t;
				break;
			}

		return topic;
	}



	public List<Topic> getAllTopics() {

		List<Topic> topics = null;

		topics = (List<Topic>) memcachedClient.get("topics");
		if (topics == null) {
			System.out.println("Couldn't get all topics from memcached");
			topics = (List<Topic>) this.sessionFactory.getCurrentSession()
					.createQuery("from Topic").list();
			memcachedClient.set("topics", 0, topics);
		}
		return topics;
		

	}
	
	
	public List<Topic> getAllTopicsForUser(User user) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("from Topic p where p.author=" + user.getId())
				.list();
	}

	
	public void updateTopicTitle(String text, long topicId) {
		Topic topic = getTopicByID(topicId);
		if (topic != null) {
			topic.setTitle(text);
			updateTopic(topic);
		}
	}

	public void updateTopicText(String text, long topicId) {
		Topic topic = getTopicByID(topicId);
		if (topic != null) {
			topic.setText(text);
			updateTopic(topic);
		}

	}
	
	public void updateTopic(Topic topic) {
		this.sessionFactory.getCurrentSession().update(topic);
	
		memcachedClient.delete("topics");
		memcachedClient.set("topics", 0, getAllTopics());
	}
	
	
	public void deleteTopic(long topiId) {
		Topic topic = (Topic) this.sessionFactory.getCurrentSession().load(
				Topic.class, topiId);
		if (topic != null) {
			this.sessionFactory.getCurrentSession().delete(topic);
		}
		
		memcachedClient.delete("topics");
		memcachedClient.set("topics", 0, getAllTopics());
		
	}

}
