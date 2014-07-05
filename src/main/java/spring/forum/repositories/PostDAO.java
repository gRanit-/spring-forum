package spring.forum.repositories;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.forum.models.Post;
import spring.forum.models.Topic;
import spring.forum.models.User;
import spring.forum.services.UsersManager;

@Repository
public class PostDAO implements Serializable {

	@Autowired
	private MemcachedClient memcachedClient;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UsersManager usersManager;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addPost(Post post) {
		this.sessionFactory.getCurrentSession().save(post);

		List<Topic> topics = (List<Topic>) this.sessionFactory
				.getCurrentSession().createQuery("from Topic").list();
		memcachedClient.set("topics", 0, topics);

	}

	public Post getPost(long id) {
		return (Post) this.sessionFactory.getCurrentSession().createQuery(
				"from Post p Where p.post_id=" + id);
	}

	public List<Post> getAllPosts() {
		List<Post> posts = null;
		/*
		 * posts = (List<Post>) memcachedClient.get("posts"); if (posts == null)
		 * { posts = (List<Post>) this.sessionFactory.getCurrentSession()
		 * .createQuery("from Post").list(); memcachedClient.set("posts", 0,
		 * posts); }
		 * 
		 * } catch (IOException ioe) { System.err
		 * .println("Couldn't create a connection to MemCachier: \nIOException "
		 * + ioe.getMessage()); }
		 */
		return posts;

	}

	public List<Post> getAllPostsForUser(User user) {
		List<Post> posts = new ArrayList<Post>();
		posts.addAll(user.getPosts());
		return posts;
	}

	public List<Post> getAllPostsForTopic(Topic topic) {
		List<Post> posts = new ArrayList<Post>();
		posts.addAll(topic.getPosts());
		return posts;
	}

	public void deletePost(long userId) {
		Post post = (Post) this.sessionFactory.getCurrentSession().load(
				Post.class, userId);
		if (post != null) {
			this.sessionFactory.getCurrentSession().delete(post);
		}
	}

	public void updatePost(Post post) {
		this.sessionFactory.getCurrentSession().update(post);
	}

	public void updatePostText(String text, long id) {
		Post post = getPost(id);
		if (post != null) {
			post.setText(text);
			updatePost(post);
		}
	}

}
