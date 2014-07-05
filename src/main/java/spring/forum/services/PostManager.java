package spring.forum.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.forum.models.Post;
import spring.forum.models.Topic;
import spring.forum.models.User;
import spring.forum.repositories.PostDAO;
import spring.forum.repositories.UserDAO;

@Service
public class PostManager {

	PostDAO postDAO;

	@Autowired
	public PostManager(PostDAO postDAO) {
		this.postDAO = postDAO;
	}

	public PostManager() {

	}
	@Transactional
	public void addPost(Post post) {
		postDAO.addPost(post);
		
	}
	@Transactional
	public List<Post> getAllPosts() {
		return postDAO.getAllPosts();
	}
	@Transactional
	public List<Post> getAllPostsForUser(User user) {
		return postDAO.getAllPostsForUser(user);
	}
	@Transactional
	public List<Post> getAllPostsForTopic(Topic topic) {
		
		return postDAO.getAllPostsForTopic(topic);
	}

	@Transactional
	public void deletePost(long userId) {
		 postDAO.deletePost(userId);
	}
	
	public PostDAO getPostDAO() {
		return postDAO;
	}

	public void setPostDAO(PostDAO postDAO) {
		this.postDAO = postDAO;
	}

}