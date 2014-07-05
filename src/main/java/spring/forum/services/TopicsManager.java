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

@Service("topicsManager")
public class TopicsManager {
	
	@Autowired
	TopicDAO topicDAO;

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
	public List<Topic> getAllTopicsForUser(User user) {
		return topicDAO.getAllTopicsForUser(user);
	}
	
	@Transactional
	public void updateTopic(Topic topic) {
		topicDAO.updateTopic(topic);
	}
	
	@Transactional
	public void updateTopicTitle(String text, long topicId) {
		topicDAO.updateTopicTitle(text, topicId);
	}
	
	@Transactional
	public void updateTopicText(String text, long topicId) {
		topicDAO.updateTopicText(text, topicId);
	}
	
	@Transactional
	public void deleteTopic(long userId) {
		topicDAO.deleteTopic(userId);
	}

	@Transactional
	public TopicDAO getTopicDAO() {
		return topicDAO;
	}


}
