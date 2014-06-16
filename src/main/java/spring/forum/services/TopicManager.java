package spring.forum.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.forum.models.Post;
import spring.forum.models.Topic;
import spring.forum.models.User;
import spring.forum.repositories.TopicDAO;
import spring.forum.repositories.UserDAO;

@Service
public class TopicManager {

	TopicDAO topicDAO;

	@Autowired
	public TopicManager(TopicDAO topicDAO) {
		this.topicDAO = topicDAO;
	}

	public TopicManager() {
	}

	@Transactional
	public void addTopic(Topic topic) {
		topicDAO.addTopic(topic);
	}

	@Transactional
	public Topic getTopicByID(long id) {
		return topicDAO.getTopicByID(id);
	}

	@Transactional
	public List<Topic> getAllTopics() {
		return topicDAO.getAllTopics();
	}

	@Transactional
	public List<Post> getAllTopicsForUser(User user) {
		return topicDAO.getAllTopicsForUser(user);
	}

	@Transactional
	public void deleteTopic(long userId) {
		topicDAO.deleteTopic(userId);
	}

	@Transactional
	public TopicDAO getTopicDAO() {
		return topicDAO;
	}

	public void setTopicDAO(TopicDAO topicDAO) {
		this.topicDAO = topicDAO;
	}

}
