package spring.forum.utils;

import java.util.ArrayList;
import java.util.List;

import net.spy.memcached.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.forum.models.Post;
import spring.forum.models.Topic;

@Service
public class MemcacheUtility {
	@Autowired
	private MemcachedClient memcachedClient;

	public void addPost(Post post) {
		long topicID = post.getTopic().getId();
		long postID = post.getId();
		String postString = "post_" + topicID + "_" + postID;
		memcachedClient.delete(postString);
		memcachedClient.add(postString, 86400, post);
		List<List<String>> postsByTopics = (List<List<String>>) memcachedClient
				.get("postsByTopicsList");
		if (postsByTopics == null) {
			addTopic(post.getTopic());
			postsByTopics = (List<List<String>>) memcachedClient
					.get("postsByTopicsList");
		}

		ListUtility.addElement(postsByTopics, String.valueOf(topicID),
				postString);
		memcachedClient.delete("postsByTopicsList");
		memcachedClient.add("postsByTopicsList", 86400, postsByTopics);
	}

	public void addTopic(Topic topic) {
		long topicID = topic.getId();
		long userID = topic.getAuthor().getId();
		String topicString = "topic_" + topicID + "_" + userID;
		memcachedClient.delete(topicString);
		memcachedClient.add(topicString, 86400, topic);

		List<List<String>> topicsByUsers = ((List<List<String>>) memcachedClient
				.get("topicsByUsers"));
		if (topicsByUsers == null)
			topicsByUsers = new ArrayList<List<String>>();
		ListUtility.addList(topicsByUsers, String.valueOf(userID));

		List<List<String>> postsByTopics = ((List<List<String>>) memcachedClient
				.get("postsByTopicsList"));
		if (postsByTopics == null)
			postsByTopics = new ArrayList<List<String>>();
		ListUtility.addList(postsByTopics, String.valueOf(topicID));

		memcachedClient.delete("topicsByUsers");
		memcachedClient.add("topicsByUsers", 86400, topicsByUsers);

		memcachedClient.delete("postsByTopicsList");
		memcachedClient.add("postsByTopicsList", 86400, postsByTopics);
	}

	public void updatePost(Post post) {
		long topicID = post.getTopic().getId();
		long postID = post.getId();
		String postString = "post_" + topicID + "_" + postID;
		memcachedClient.delete(postString);
		memcachedClient.add(postString, 86400, post);
	}

	public void updateTopic(Topic topic) {
		long topicID = topic.getId();
		long userID = topic.getAuthor().getId();
		String topicString = "topic_" + topicID + "_" + userID;
		memcachedClient.delete(topicString);
		memcachedClient.add(topicString, 86400, topic);
	}

	public void deletePost(Post post) {
		long topicID = post.getTopic().getId();
		long postID = post.getId();
		String postString = "post_" + topicID + "_" + postID;
		memcachedClient.delete(postString);
		List<List<String>> postsByTopicsList = (List<List<String>>) memcachedClient
				.get("postsByTopicsList");

		ListUtility.deleteElement(postsByTopicsList, String.valueOf(topicID),
				String.valueOf(postID));
	}

	public void deleteTopic(Topic topic) {
		long topicID = topic.getId();
		long userID = topic.getAuthor().getId();
		String topicString = "topic_" + topicID + "_" + userID;
		memcachedClient.delete(topicString);
		List<List<String>> postsByTopicsList = (List<List<String>>) memcachedClient
				.get("postsByTopicsList");

		List<List<String>> topicsByUsers = ((List<List<String>>) memcachedClient
				.get("topicsByUsers"));

		List<String> postsList = ListUtility.searchForList(postsByTopicsList,
				String.valueOf(topicID));
		for (String post : postsList)
			memcachedClient.delete(post);
		postsByTopicsList.remove(postsList);
		ListUtility.deleteElement(topicsByUsers, String.valueOf(userID),
				String.valueOf(topicID));
		memcachedClient.delete(topicString);

	}

	public List<Post> getAllPosts() {
		List<List<String>> postList = (List<List<String>>) memcachedClient
				.get("postsByTopicsList");
		if (postList == null)
			return null;
		else {
			List<Post> posts = new ArrayList<Post>();
			for (List<String> list : postList)
				for (int i = 1; i < list.size(); i++)
					posts.add((Post) memcachedClient.get(list.get(i)));
			return posts;
		}
	}

	public List<Topic> getAllTopics() {
		List<List<String>> topicList = (List<List<String>>) memcachedClient
				.get("topicsByUsers");
		if (topicList == null)
			return null;
		else {
			List<Topic> topics = new ArrayList<Topic>();
			for (List<String> list : topicList)
				for (int i = 1; i < list.size(); i++)
					topics.add((Topic) memcachedClient.get(list.get(i)));
			return topics;
		}
	}

	public List<Post> getPostsByTopic(long topicID) {
		List<List<String>> postsByTopicsList = (List<List<String>>) memcachedClient
				.get("postsByTopicsList");
		List<String> postsStrings = ListUtility.searchForList(
				postsByTopicsList, String.valueOf(topicID));
		if (postsStrings == null)
			return null;
		else {
			List<Post> posts = new ArrayList<Post>();
			for (String postString : postsStrings)
				posts.add((Post) memcachedClient.get(postString));

			return posts;
		}

	}

	public List<Topic> getTopicsByUser(long userID) {

		List<List<String>> topicsByUserList = (List<List<String>>) memcachedClient
				.get("topicsByUsers");
		List<String> topicsStrings = ListUtility.searchForList(
				topicsByUserList, String.valueOf(userID));
		if (topicsStrings == null)
			return null;
		else {
			List<Topic> topics = new ArrayList<Topic>();
			for (String topicString : topicsStrings)
				topics.add((Topic) memcachedClient.get(topicString));

			return topics;
		}

	}

}
