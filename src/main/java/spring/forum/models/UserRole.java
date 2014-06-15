package spring.forum.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRole {



	@Id
	@Column(name = "ROLE_ID", nullable = false, unique = true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer userRoleId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMAIL", nullable = false)
	private User user;

	@Column(name = "ROLE", nullable = false, length = 45)
	private String role;

	
	public UserRole(Integer userRoleId, User user, String role) {
		super();
		this.userRoleId = userRoleId;
		this.user = user;
		this.role = role;
	}
	
	
	public UserRole(User user, String role) {
		super();
		this.user = user;
		this.role = role;
	}


	public Integer getUserRoleId() {
		return this.userRoleId;
	}

	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
