package spring.forum.services;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.forum.models.Post;
import spring.forum.models.Topic;
import spring.forum.models.User;
import spring.forum.repositories.PostDAO;
import spring.forum.repositories.UserDAO;

@Service("postsManager")
public class PostsManager {
	@Autowired
	PostDAO postDAO;

	@Autowired
	public PostsManager(PostDAO postDAO) {
		this.postDAO = postDAO;
	}

	@Transactional
	public void addPost(Post post) {
		postDAO.addPost(post);
		
	}
	
	@Transactional
	public Post getPost(long id) {
		return postDAO.getPost(id);
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
		
		List<Post> posts=postDAO.getAllPostsForTopic(topic);
		if(posts==null)
			Hibernate.initialize(topic.getPosts());
		
		return posts;
	}

	@Transactional
	public void deletePost(long id) {
		 postDAO.deletePost(id);
	}
	
	@Transactional
	public void updatePostText(String text, long id) {
		postDAO.updatePostText(text, id);
	}
	
	@Transactional
	public void updatePost(Post post) {
		postDAO.updatePost(post);
	}
	
}