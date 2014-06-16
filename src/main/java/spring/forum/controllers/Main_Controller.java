package spring.forum.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import spring.forum.models.Post;
import spring.forum.models.Topic;
import spring.forum.models.User;
import spring.forum.models.UserRole;
import spring.forum.services.TopicManager;
import spring.forum.services.UserManager;
import spring.forum.services.PostManager;
import spring.forum.services.UserRoleManager;
import spring.forum.controllers.DateUtils;
@Controller
public class Main_Controller {
	@Autowired
	private UserManager userManager;
	@Autowired
	private	 UserRoleManager roleManager;
	@Autowired
	private	 TopicManager topicManager;
	@Autowired
	private	 PostManager postManager;
//	@Autowired
//	private	 UserDAO userDAO;
	
	
	
	@RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
	public ModelAndView defaultPage(HttpSession session) {
		if(session==null)
			return new ModelAndView();
		System.out.println("WELCOME!!");
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security + Hibernate Example");
		model.addObject("message", "This is default page!");
		model.setViewName("hello");
		return model;

	}

	
	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security + Hibernate Example");
		model.addObject("message", "This page is for ROLE_ADMIN only!");
		model.setViewName("admin");

		return model;

	}

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

	// customize the error message
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
	
	
	// for 403 access denied page
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
	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public ModelAndView createNewUser(Map<String, Object> model) {
		return new ModelAndView("add_user_form","user",new User());
	}
			
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String createNewUser(
			final @Valid @ModelAttribute spring.forum.models.User user,
			final BindingResult result,
			final SessionStatus status) {
		
		user.setToken("token");
		user.setEnabled(true);
		UserRole role=new UserRole(user, "ROLE_USER");

		user.getUserRole().add(role);

		
		userManager.addUser(user);
		
		//ModelAndView model =new ModelAndView();
		System.out.println(user.getEmail());
		return "login";

	}
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@RequestMapping(value = "/addTopic", method = RequestMethod.GET)
	public ModelAndView createTopic(Map<String, Object> model){
		return new ModelAndView("add_topic_form","topic",new Topic());
		
	}
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@RequestMapping(value = "/addTopic", method = RequestMethod.POST)
	public String createTopic(final @Valid @ModelAttribute spring.forum.models.Topic topic,
			final BindingResult result,
			final SessionStatus status,Authentication authentication){
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User usr=userManager.getUserByEmail(userDetails.getUsername());
		topic.setAuthor(usr);
		topic.setDate(DateUtils.getCurrentDate());
		topicManager.addTopic(topic);
		
		return "showTopic/"+topic.getId();
		
	}
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "/deleteTopic/{topicID}", method = RequestMethod.GET)
	public String deleteTopic(@PathVariable String topicID){
		topicManager.deleteTopic(Integer.parseInt(topicID));
		return "welcome";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "/deletePost/{postID}", method = RequestMethod.GET)
	public String deletePost(@PathVariable String postID){
		postManager.deletePost(Integer.parseInt(postID));
		return "welcome";
	}
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "/deleteUser/{userID}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable String userID){
		userManager.deleteUser(Integer.parseInt(userID));
		return "welcome";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@RequestMapping(value = "/showTopic/{topicID}", method = RequestMethod.GET)
	public ModelAndView showTopic(@PathVariable String topicID,Model model){
		Topic topic=topicManager.getTopicByID(Integer.parseInt(topicID));
		List<Post> posts=postManager.getAllPostsForTopic(topic);
;
		model.addAttribute("topic", topic);
		model.addAttribute("posts",posts);
		
		return new ModelAndView("show_topic","post",new Post()); 	
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@RequestMapping(value = "/addPost", method = RequestMethod.GET)
	public ModelAndView addPost(){
		return new ModelAndView("add_post_form","post",new Post());
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@RequestMapping(value = "/addPostToTopic/{topicID}", method = RequestMethod.POST)
	public String addPost(@PathVariable String topicID,
			final @Valid @ModelAttribute spring.forum.models.Post post,
			final BindingResult result,
			final SessionStatus status,Authentication authentication){
		
		Topic topic=topicManager.getTopicByID(Integer.parseInt(topicID));
		post.setCreationDate(spring.forum.controllers.DateUtils.getCurrentDate());
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User usr=userManager.getUserByEmail(userDetails.getUsername());
		post.setAuthor(usr);
		post.setTopic(topic);
		postManager.addPost(post);

		return "show_topic/"+topic.getId(); 
	}
	
}
