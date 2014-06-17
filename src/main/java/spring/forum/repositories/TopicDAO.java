package spring.forum.repositories;

import java.io.IOException;
import java.util.List;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;

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
		
		System.out.print(System.getenv("MEMCACHIER_USERNAME")+"   -----    " +
		            System.getenv("MEMCACHIER_PASSWORD"));
		AuthDescriptor ad = new AuthDescriptor(new String[] { "PLAIN" },
		        new PlainCallbackHandler(System.getenv("MEMCACHIER_USERNAME"),
		            System.getenv("MEMCACHIER_PASSWORD")));
		
		try {
		      MemcachedClient mc = new MemcachedClient(
		          new ConnectionFactoryBuilder()
		              .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
		              .setAuthDescriptor(ad).build(),
		          AddrUtil.getAddresses(System.getenv("MEMCACHIER_SERVERS")));
		      mc.set("foo", 0, "bar");
		      System.out.println(mc.get("foo"));
		    } catch (IOException ioe) {
		      System.err.println("Couldn't create a connection to MemCachier: \nIOException "
		              + ioe.getMessage());
		    }
		
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
