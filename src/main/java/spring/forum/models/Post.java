package spring.forum.models;

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
@Table(name = "POSTS")
public class Post  {

	@Id
	@Column(name = "POST_ID",columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "TEXT")
	private String text;

	@Column(name = "CREATION_DATE")
	private String creationDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User author;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TOPIC_ID", nullable = false)
	private Topic topic;

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public long getId() {
		return id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
}
