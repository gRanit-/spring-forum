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

import spring.forum.models.Post;
import spring.forum.models.Topic;
import spring.forum.models.User;

public class TopicDAO implements Serializable{

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
	@SuppressWarnings("unchecked")
	public List<Topic> getAllTopics() {
		MemcachedClient memcachedClient=null;
		List<Topic> topics=null;
		AuthDescriptor ad = new AuthDescriptor(new String[] { "PLAIN" },
		        new PlainCallbackHandler(System.getenv("MEMCACHIER_USERNAME"),
		            System.getenv("MEMCACHIER_PASSWORD")));
		
		try {
			memcachedClient = new MemcachedClient(
		          new ConnectionFactoryBuilder()
		              .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
		              .setAuthDescriptor(ad).build(),
		          AddrUtil.getAddresses(System.getenv("MEMCACHIER_SERVERS")));
		      
		     topics=(List<Topic>)memcachedClient.get("topics");
		    if(topics==null) {		  
		    		  topics=(List<Topic>) this.sessionFactory.getCurrentSession()
							.createQuery("from Topic").list();
		    		  memcachedClient.set("topics", 0, topics);
		    }	
		    
			

		      
		    } catch (IOException ioe) {
		      System.err.println("Couldn't create a connection to MemCachier: \nIOException "
		              + ioe.getMessage());
		    }
		
			return topics;
		
		

		
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
