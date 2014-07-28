package spring.forum.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.spy.memcached.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.forum.models.Post;
import spring.forum.models.Topic;
import spring.forum.utils.CollectionUtility;
import spring.forum.utils.CollectionUtility.*;

@Service
public class MemcachedService {
	@Autowired
	private MemcachedClient memcachedClient;

	public void addPost(Post post) {
		Set<CollectionUtility.Element> set = (Set<CollectionUtility.Element>) memcachedClient
				.get("posts_1");
		int i = 1;
		if (set == null) {
			set = new TreeSet<Element>(new ComparatorUtil());

		} else
			while (set.size() > 1000) {
				i++;
				set = (Set<CollectionUtility.Element>) memcachedClient
						.get("posts_" + i);
				if (set == null)
					set = new TreeSet<Element>(new ComparatorUtil());
			}

		set.add(new CollectionUtility.Element((post.getAuthor().getId()), post
				.getId(), post.getTopic().getId()));

		memcachedClient.delete("posts_" + i);
		memcachedClient.add("posts_" + i, 86400, set);
		String postString = "p_" + post.getId();
		memcachedClient.add(postString, 86400, post);

	}

	public void addTopic(Topic topic) {

		Set<CollectionUtility.Element> set = (Set<CollectionUtility.Element>) memcachedClient
				.get("topics_1");
		int i = 1;
		if (set == null) {
			set = new TreeSet<Element>(new ComparatorUtil());

		} else
			while (set.size() > 1000) {
				i++;
				set = (Set<CollectionUtility.Element>) memcachedClient
						.get("topics_" + i);
				if (set == null)
					set = new TreeSet<Element>(new ComparatorUtil());
			}

		set.add(new Element((topic.getAuthor().getId()), (long) -1, topic
				.getId()));

		memcachedClient.delete("topics_" + i);
		memcachedClient.add("topics_" + i, 86400, set);

		String topicString = "t_" + topic.getId();
		memcachedClient.add(topicString, 86400, topic);

	}

	public void updatePost(Post post) {
		long topicID = post.getTopic().getId();
		long postID = post.getId();

		Set<CollectionUtility.Element> set = (Set<CollectionUtility.Element>) memcachedClient
				.get("posts_1");
		Element element = new Element(post.getAuthor().getId(), postID, topicID);
		int i = 1;
		while (!set.remove(element)) {
			i++;
			set = (Set<CollectionUtility.Element>) memcachedClient.get("posts_"
					+ i);

		}
		set.add(element);
		memcachedClient.delete("posts_" + i);
		memcachedClient.add("posts_" + i, 86400, set);

		String postString = "p_" + post.getId();
		memcachedClient.delete(postString);
		memcachedClient.add(postString, 86400, post);
	}

	public void updateTopic(Topic topic) {
		long topicID = topic.getId();
		long userID = topic.getAuthor().getId();
		Set<CollectionUtility.Element> set = (Set<CollectionUtility.Element>) memcachedClient
				.get("topics_1");
		Element element = new Element(topic.getAuthor().getId(), (long) -1,
				topicID);
		int i = 1;
		while (!set.remove(element)) {
			i++;
			set = (Set<CollectionUtility.Element>) memcachedClient
					.get("topics_" + i);

		}
		set.add(element);
		memcachedClient.delete("topics_" + i);
		memcachedClient.add("topics_" + i, 86400, set);

		String topicString = "t_" + topic.getId();
		memcachedClient.delete(topicString);
		memcachedClient.add(topicString, 86400, topic);
	}

	public void deletePost(Post post) {
		long topicID = post.getTopic().getId();
		long postID = post.getId();

		Set<CollectionUtility.Element> set = (Set<CollectionUtility.Element>) memcachedClient
				.get("posts_1");
		Element element = new Element(post.getAuthor().getId(), postID, topicID);
		int i = 1;
		while (!set.remove(element)) {
			i++;
			set = (Set<CollectionUtility.Element>) memcachedClient.get("posts_"
					+ i);

		}

		String postString = "p_" + post.getAuthor().getId() + "_"
				+ post.getTopic().getId() + "_" + post.getId();
		memcachedClient.delete(postString);
	}

	public void deleteTopic(Topic topic) {
		long topicID = topic.getId();
		long userID = topic.getAuthor().getId();
		Set<CollectionUtility.Element> set = (Set<CollectionUtility.Element>) memcachedClient
				.get("topics_1");
		Element element = new Element(topic.getAuthor().getId(), (long) -1,
				topicID);
		int i = 1;
		while (!set.remove(element)) {
			i++;
			set = (Set<CollectionUtility.Element>) memcachedClient
					.get("topics_" + i);

		}

		String topicString = "t_" + topic.getId();
		memcachedClient.delete(topicString);

	}

	public Post getPost(long postID) {
		String postString = "p_" + postID;
		return (Post) memcachedClient.get(postString);
	}

	public Topic getTopic(long topicID) {
		return (Topic) memcachedClient.get("t_" + topicID);
	}

	public List<Post> getAllPosts() {
		Set<CollectionUtility.Element> set = (Set<CollectionUtility.Element>) memcachedClient
				.get("posts_1");
		List<Post> postList = new ArrayList<Post>();
		int i = 1;
		if (set != null)
			while (set.size() > 0) {
				i++;
				for (Element e : set) {
					long postID = e.postID;
					String postString = "p_" + postID;
					Post post = (Post) memcachedClient.get(postString);
					if (post != null)
						postList.add(post);
				}

				set = (Set<CollectionUtility.Element>) memcachedClient
						.get("posts_" + i);
				if (set == null)
					break;
			}
		return postList;
	}

	public List<Topic> getAllTopics() {
		Set<CollectionUtility.Element> set = (Set<CollectionUtility.Element>) memcachedClient
				.get("topics_1");
		List<Topic> topicsList = new ArrayList<Topic>();
		int i = 1;
		if (set != null)
			while (set.size() > 0) {
				i++;
				for (Element e : set) {
					long topicID = e.topicID;
					String topicString = "t_" + topicID;
					Topic topic = (Topic) memcachedClient.get(topicString);
					if (topic != null)
						topicsList.add(topic);
				}

				set = (Set<CollectionUtility.Element>) memcachedClient
						.get("topics_" + i);
				if (set == null)
					break;
			}
		return topicsList;
	}

	public List<Post> getPostsByTopic(long topicID) {
		TreeSet<CollectionUtility.Element> set = (TreeSet<CollectionUtility.Element>) memcachedClient
				.get("posts_1");
		List<Post> postList = new ArrayList<Post>();
		int i = 1;
		if (set != null)
			while (set.size() > 0) {
				i++;
				Set<CollectionUtility.Element> subset = set.subSet(new Element(
						(long) 0, (long) 0, topicID), new Element((long) 0,
						(long) 0, topicID + 1));
				for (Element e : subset) {
					long postID = e.postID;
					String postString = "p_" + postID;
					Post post = (Post) memcachedClient.get(postString);
					if (post != null)
						postList.add(post);
				}

				set = (TreeSet<CollectionUtility.Element>) memcachedClient
						.get("posts_" + i);
				if (set == null)
					break;
			}
		return postList;
	}

	public List<Topic> getTopicsByUser(long userID) {

		TreeSet<CollectionUtility.Element> set = (TreeSet<CollectionUtility.Element>) memcachedClient
				.get("topics_1");
		List<Topic> topicsList = new ArrayList<Topic>();
		int i = 1;
		if (set != null)
			while (set.size() > 0) {
				i++;
				Set<CollectionUtility.Element> subset = set.subSet(new Element(
						userID, (long) -1, (long) 0), new Element(userID + 1,
						(long) -1, (long) 0));
				for (Element e : set) {
					long topicID = e.topicID;
					String topicString = "t_" + topicID;
					Topic topic = (Topic) memcachedClient.get(topicString);
					if (topic != null)
						topicsList.add(topic);
				}

				set = (TreeSet<CollectionUtility.Element>) memcachedClient
						.get("topics_" + i);
				if (set == null)
					break;
			}
		return topicsList;
	}
}
