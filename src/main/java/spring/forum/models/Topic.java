package spring.forum.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TOPICS")
public class Topic {

	@Id
	@Column(name = "TOPIC_ID", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "TEXT")
	private String text;

	@Column(name = "CREATION_DATE")
	private String date;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMAIL", nullable = false)
	private User author;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "topic")
	private Set<Post> posts;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FORUM_ID", nullable = false)
	private Forum forum;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public long getId() {
		return id;
	}

}
