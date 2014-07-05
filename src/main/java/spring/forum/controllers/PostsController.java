package spring.forum.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import spring.forum.models.Post;
import spring.forum.models.Topic;
import spring.forum.models.User;
import spring.forum.services.PostsManager;
import spring.forum.services.TopicsManager;
import spring.forum.services.UserRoleManager;
import spring.forum.services.UsersManager;

@Controller
public class PostsController {
	@Autowired
	private UsersManager usersManager;
	@Autowired
	private UserRoleManager roleManager;
	@Autowired
	private PostsManager postsManager;
	@Autowired
	private	 TopicsManager topicManager;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "/deletePost/{postID}", method = RequestMethod.GET)
	public String deletePost(@PathVariable String postID){
		postsManager.deletePost(Integer.parseInt(postID));
		return "welcome";
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
			final SessionStatus status,Authentication authentication, Model model){
		
		Topic topic=topicManager.getTopicByID(Integer.parseInt(topicID));
		post.setCreationDate(spring.forum.controllers.DateUtils.getCurrentDate());
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User usr=usersManager.getUserByEmail(userDetails.getUsername());
		post.setAuthor(usr);
		post.setTopic(topic);
		postsManager.addPost(post);

		return "showAllTopics";
	}
}
