package spring.forum.models;

import java.util.HashSet;
import java.util.Set;
import spring.forum.models.UserRole;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "USERS")
public class User {
	@Id
	@Column(name = "ID",nullable=false,unique=true)
	@GeneratedValue
	private long id;

	@Column(name = "login",nullable=false,unique=true)
	private String login;
	
	@Column(name = "TOKEN",nullable=false)
	private String token;
	
	@Column(name = "EMAIL",nullable=false,unique=true)
	@NotNull
	private String email;

	@Column(name = "PASSWORD",nullable=false)
	@NotNull
	private String password;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<UserRole> userRole;
	
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	
	public boolean isEnabled() {
		return this.enabled;
	}
 
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
 
	public long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public Set<UserRole> getUserRole() {
		return this.userRole;
	}
 
	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}
