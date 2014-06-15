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
public class Post {

	@Id
	@Column(name = "POST_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "TEXT")
	private String text;

	@Column(name = "CREATION_DATE")
	private String creatonDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMAIL", nullable = false)
	private User author;

	@ManyToOne(fetch = FetchType.LAZY)
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

	public String getCreatonDate() {
		return creatonDate;
	}

	public void setCreatonDate(String creatonDate) {
		this.creatonDate = creatonDate;
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
