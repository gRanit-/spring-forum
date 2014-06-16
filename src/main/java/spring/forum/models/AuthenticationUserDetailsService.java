package spring.forum.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import spring.forum.repositories.UserDAO;
import spring.forum.repositories.UserRoleDAO;

@Service("userDetailsService")
public class AuthenticationUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;

	@Autowired 
	UserRoleDAO userRoleDAO;
	public UserDetails loadUserByUsername(final String mail)
			throws UsernameNotFoundException {
		spring.forum.models.User user = userDAO.getUserByEmail(mail); // Pobieramy
																		// usera
																		// z db
																		// przy
																		// pomocy
																		// Hibernate
		System.out.println("MY MAIL::::::::::" +user.getEmail());
		for(UserRole ur: user.getUserRole()){
			System.out.println("My Role "+ur.getRole());
		}
		
		
		List<GrantedAuthority> authorities = buildUserAuthority(userRoleDAO.getRole(user));

		return buildUserForAuthentication(user, authorities);
	}

	public UserRoleDAO getUserRoleDAO() {
		return userRoleDAO;
	}

	public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
	}

	private User buildUserForAuthentication(spring.forum.models.User user, // Tworzymy
																			// obiekt
																			// org.springframework.security.core.userdetails.User
																			// na
																			// potrzeby
																			// auhentication
			List<GrantedAuthority> authorities) {
		return new User(user.getEmail(), user.getPassword(), user.isEnabled(),
				true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(List<UserRole> userRoles) {// sprawdzamy role
																				// i
																				// na
																				// podstawie
																				// tego
																				// tworzymy
																				// userAuthorities

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (UserRole userRole : userRoles) {
			System.out.println(userRole.getRole());
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(
				setAuths);

		return Result;
	}
	
	public UserDAO getUserDao() {
		return userDAO;
	}
 
	public void setUserDAO(UserDAO userDao) {
		this.userDAO = userDao;
	}

}
