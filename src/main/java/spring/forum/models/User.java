package spring.forum.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "USERS")
public class User {

	private long id;

	private String nick;

	
	private String token;

	
	private String email;

	private String password;

	
	private Set<UserRole> userRole= new HashSet<UserRole>(0);

	
	private boolean enabled;

	private Set<Post> posts=new HashSet<Post>(0);

	private Set<Topic> topics=new HashSet<Topic>(0);
	public User(){};
	public User(String email, String password, boolean enabled) {
		this.email = email;
		this.password = password;
		this.enabled = enabled;
	}
 
	public User(String email, String password, 
		boolean enabled, Set<UserRole> userRole) {
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.userRole = userRole;
	}
 
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "author", cascade = CascadeType.ALL)
	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "author", cascade = CascadeType.ALL)
	public Set<Topic> getTopics() {
		return topics;
	}

	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}
	
	@Column(name = "enabled", nullable = false)
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "nick", nullable = false, unique = true)
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	@Column(name = "PASSWORD", nullable = false)
	@NotNull
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
	
	@Column(name = "EMAIL", nullable = false, unique = true)
	@NotNull
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	public Set<UserRole> getUserRole() {
		return this.userRole;
	}

	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}

	@Column(name = "TOKEN", nullable = true)
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Id
	@Column(name = "USER_ID", columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
