package spring.forum.repositories;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.forum.models.Topic;
import spring.forum.models.User;
import spring.forum.services.MemcachedService;

@Repository
public class TopicDAO implements Serializable {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private MemcachedService memcachedService;

	public void addTopic(Topic topic) {
		this.sessionFactory.getCurrentSession().save(topic);
		memcachedService.addTopic(topic);

	}

	public Topic getTopicByID(long id) {

		Topic topic = memcachedService.getTopic(id);
		if (topic == null) {
			System.out.println("Couldn't get topic from memcached");
			topic = (Topic) this.sessionFactory.getCurrentSession()
					.createQuery("from Topic t where t.id=" + id).list().get(0);
			memcachedService.addTopic(topic);

		}

		return topic;
	}

	public List<Topic> getAllTopics() {

		List<Topic> topics = null;

		topics = (List<Topic>) memcachedService.getAllTopics();
		if (topics == null) {
			System.out.println("Couldn't get all topics from memcached");
			topics = (List<Topic>) this.sessionFactory.getCurrentSession()
					.createQuery("from Topic").list();
			for (Topic topic : topics)
				memcachedService.addTopic(topic);
		}
		return topics;

	}

	public List<Topic> getAllTopicsForUser(User user) {
		List<Topic> topics = memcachedService.getTopicsByUser(user.getId());
		if (topics == null) {
			topics = this.sessionFactory.getCurrentSession()
					.createQuery("from Topic p where p.author=" + user.getId())
					.list();
			for (Topic topic : topics)
				memcachedService.addTopic(topic);

		}
		return topics;
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
		memcachedService.updateTopic(topic);
	}

	public void deleteTopic(long topiId) {
		Topic topic = (Topic) this.sessionFactory.getCurrentSession().load(
				Topic.class, topiId);
		if (topic != null) {
			this.sessionFactory.getCurrentSession().delete(topic);
		}
		memcachedService.deleteTopic(topic);

	}

}
