package spring.forum.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "FORUM")
public class Forum {
	@Id
	@Column(name = "FORUM_ID",columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "CREATION_DATE")
	private String date;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "forum")
	private Set<Topic> topics;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Set<Topic> getTopics() {
		return topics;
	}

	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}

	public long getId() {
		return id;
	}
}
