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
		
		MemcachedClient memcachedClient = null;
		List<Post> posts = null;
		AuthDescriptor ad = new AuthDescriptor(new String[] { "PLAIN" },
				new PlainCallbackHandler(System.getenv("MEMCACHIER_USERNAME"),
						System.getenv("MEMCACHIER_PASSWORD")));

		try {
			memcachedClient = new MemcachedClient(
					new ConnectionFactoryBuilder()
							.setProtocol(
									ConnectionFactoryBuilder.Protocol.BINARY)
							.setAuthDescriptor(ad).build(),
					AddrUtil.getAddresses(System.getenv("MEMCACHIER_SERVERS")));

	//		posts = (List<Post>) this.sessionFactory.getCurrentSession()
	//				.createQuery("from Post").list();
		///	memcachedClient.set("posts", 0, posts);
			List<Topic> topics = (List<Topic>) this.sessionFactory.getCurrentSession()
					.createQuery("from Topic").list();
			memcachedClient.set("topics", 0, topics);

		} catch (IOException ioe) {
			System.err
					.println("Couldn't create a connection to MemCachier: \nIOException "
							+ ioe.getMessage());
		}

	}

	public List<Post> getAllPosts() {
		// TODO users vs user
		MemcachedClient memcachedClient = null;

		List<Post> posts = null;
		AuthDescriptor ad = new AuthDescriptor(new String[] { "PLAIN" },
				new PlainCallbackHandler(System.getenv("MEMCACHIER_USERNAME"),
						System.getenv("MEMCACHIER_PASSWORD")));

		try {
			memcachedClient = new MemcachedClient(
					new ConnectionFactoryBuilder()
							.setProtocol(
									ConnectionFactoryBuilder.Protocol.BINARY)
							.setAuthDescriptor(ad).build(),
					AddrUtil.getAddresses(System.getenv("MEMCACHIER_SERVERS")));

			posts = (List<Post>) memcachedClient.get("posts");
			if (posts == null) {
				posts = (List<Post>) this.sessionFactory.getCurrentSession()
						.createQuery("from Post").list();
				memcachedClient.set("posts", 0, posts);
			}

		} catch (IOException ioe) {
			System.err
					.println("Couldn't create a connection to MemCachier: \nIOException "
							+ ioe.getMessage());
		}

		return posts;

	}

	public List<Post> getAllPostsForUser(User user) {
		// return this.sessionFactory.getCurrentSession()
		// .createQuery("from Post p where p.author="+user.getId()).list();
		List<Post> posts = new ArrayList<Post>();
		posts.addAll(user.getPosts());
		return posts;
	}

	public List<Post> getAllPostsForTopic(Topic topic) {
		List<Post> posts = new ArrayList<Post>();
		posts.addAll(topic.getPosts());
		return posts;
	}

	// public User getAuthor(){
	//
	// }

	public void deletePost(long userId) {
		Post post = (Post) this.sessionFactory.getCurrentSession().load(
				Post.class, userId);
		if (post != null) {
			this.sessionFactory.getCurrentSession().delete(post);
		}
	}

}
