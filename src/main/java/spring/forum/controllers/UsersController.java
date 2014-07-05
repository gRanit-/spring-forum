package spring.forum.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import spring.forum.models.User;
import spring.forum.models.UserRole;
import spring.forum.services.UsersManager;
import spring.forum.services.UserRoleManager;

@Controller
public class UsersController {
	@Autowired
	private UsersManager usersManager;
	@Autowired
	private UserRoleManager roleManager;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			HttpServletRequest request) {
		System.out.println(request.getAttribute("email"));
		ModelAndView model = new ModelAndView();
		if (error != null) {

			System.out.println("login error");

			model.addObject("error",
					getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;

	}

	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession()
				.getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Invalid username and password!";
		}

		return error;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		return "redirect:j_spring_security_logout";

	}

	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public ModelAndView createNewUser(Map<String, Object> model) {
		return new ModelAndView("add_user_form", "user", new User());
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String createNewUser(
			final @Valid @ModelAttribute spring.forum.models.User user,
			final BindingResult result, final SessionStatus status) {

		user.setToken("token");
		user.setEnabled(true);
		UserRole role = new UserRole(user, "ROLE_USER");

		user.getUserRole().add(role);

		usersManager.addUser(user);

		System.out.println(user.getEmail());
		return "login";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);

			model.addObject("username", userDetail.getUsername());

		}

		model.setViewName("403");
		return model;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "/deleteUser/{userID}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable String userID) {
		usersManager.deleteUser(Integer.parseInt(userID));
		return "welcome";
	}
}
