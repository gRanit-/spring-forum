package spring.forum.repositories;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.forum.models.Post;
import spring.forum.models.Topic;
import spring.forum.models.User;
import spring.forum.services.MemcachedService;
import spring.forum.services.TopicsManager;
import spring.forum.services.UsersManager;

@Repository
public class PostDAO implements Serializable {

	@Autowired
	private MemcachedService memcachedService;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UsersManager usersManager;


	public void addPost(Post post) {
		this.sessionFactory.getCurrentSession().save(post);
		memcachedService.addPost(post);
	}

	public Post getPost(long id) {
		Post post=memcachedService.getPost(id);
		
		if(post==null) {
			post=(Post) this.sessionFactory.getCurrentSession()
					.createQuery("from Post p Where p.id=" + id).list().get(0);
			memcachedService.addPost(post);
		}
		return post;
	}
	public Post getPost(long topicID,long postID) {
		Post post=memcachedService.getPost(postID);
		
		if(post==null) {
			post=(Post) this.sessionFactory.getCurrentSession()
					.createQuery("from Post p Where p.id=" + postID).list().get(0);
			memcachedService.addPost(post);
		}
		return post;
		
	}
	public List<Post> getAllPosts() {
		List<Post> posts = null;
	
		 posts = (List<Post>)memcachedService.getAllPosts();
		 if (posts == null)
			 posts = (List<Post>) this.sessionFactory.getCurrentSession().createQuery("from Post").list(); 
		 else if(posts.size()==0)
			 posts = (List<Post>) this.sessionFactory.getCurrentSession().createQuery("from Post").list();
		return posts;

	}

	public List<Post> getAllPostsForUser(User user) {
		List<Post> posts = new ArrayList<Post>();
		// posts.addAll(user.getPosts());
		// this.sessionFactory.getCurrentSession().beginTransaction();
		return posts;
	}

	public List<Post> getAllPostsForTopic(Topic topic) {
		return memcachedService.getPostsByTopic(topic.getId());
	}

	public void deletePost(long userId) {
		Post post = (Post) this.sessionFactory.getCurrentSession().load(
				Post.class, userId);
		if (post != null) {
			this.sessionFactory.getCurrentSession().delete(post);
		}
		memcachedService.deletePost(post);
	}

	public void updatePost(Post post) {
		this.sessionFactory.getCurrentSession().update(post);
		memcachedService.updatePost(post);
	}

	public void updatePostText(String text, long id) {
		Post post = getPost(id);
		if (post != null) {
			post.setText(text);
			updatePost(post);
		}
	}

}
