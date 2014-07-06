package spring.forum.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	private TopicsManager topicManager;

	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@RequestMapping(value = "/deletePost/{postID}", method = RequestMethod.DELETE)
	public String deletePost(@PathVariable String postID) {
		postsManager.deletePost(Integer.parseInt(postID));
		return "";
	}

	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@RequestMapping(value = "/getPost/{postID}", method = RequestMethod.DELETE)
	public String getPost(@PathVariable String postID) {
		postsManager.getPost(Integer.parseInt(postID));
		return "welcome";
	}

	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@RequestMapping(value = "/addPostToTopic/{topicID}", method = RequestMethod.GET)
	public ModelAndView addPost(@PathVariable String topicID,
			HttpServletRequest request, Model model) {
		long id = (Integer.parseInt(topicID));
		model.addAttribute("id", id);
		return new ModelAndView("add_post_form", "post", new Post());
	}

	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@RequestMapping(value = "/showPostsForTopic/{topicID}", method = RequestMethod.GET)
	public ModelAndView showPostsForTopic(@PathVariable String topicID,
			HttpServletRequest request, Model model) {

		long id = (Integer.parseInt(topicID));
		model.addAttribute("id", id);
		Topic topic=topicManager.getTopicByID(id);
		List<Post> posts = postsManager.getAllPostsForTopic(topic);
		model.addAttribute("posts", posts);
		return new ModelAndView("show_topic_posts");
	}

	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	
	@RequestMapping(value = "/addPostToTopic/{topicID}",method=RequestMethod.POST,
			consumes={"application/json", "application/xml","application/x-www-form-urlencoded","text/html"})

	public  @ResponseBody String addPostToTopic( @RequestBody String text,@PathVariable String topicID) {
		System.out.println("INININININININI");

		Post post=new Post();
		post.setText(text);
		Topic topic = topicManager.getTopicByID(Integer.parseInt(topicID));
		post.setCreationDate(spring.forum.controllers.DateUtils
				.getCurrentDate());
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User usr = usersManager.getUserByEmail(userDetails.getUsername());
		post.setAuthor(usr);
		post.setTopic(topic);
		postsManager.addPost(post);

		return "ok";
	}
}
