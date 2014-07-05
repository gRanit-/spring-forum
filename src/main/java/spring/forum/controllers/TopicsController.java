package spring.forum.controllers;

import java.util.List;
import java.util.Map;

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
import spring.forum.services.UsersManager;
import spring.forum.services.UserRoleManager;

@Controller
public class TopicsController {
	
	@Autowired
	private UsersManager usersManager;
	@Autowired
	private UserRoleManager roleManager;
	@Autowired
	private	 TopicsManager topicManager;
	@Autowired
	private	 PostsManager postsManager;
	
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@RequestMapping(value = "/showTopic/{topicID}", method = RequestMethod.GET)
	public ModelAndView showTopic(@PathVariable String topicID,Model model){
		Topic topic=topicManager.getTopicByID(Integer.parseInt(topicID));
		List<Post> posts=postsManager.getAllPostsForTopic(topic);

		model.addAttribute("topic", topic);
		model.addAttribute("posts",posts);
		
		return new ModelAndView("show_topic","post",new Post()); 	
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@RequestMapping(value = { "/", "/welcome**","/showAllTopics"}, method = RequestMethod.GET)
	public ModelAndView showAllTopics(Model model){
		
		List<Topic> topics=topicManager.getAllTopics();
		model.addAttribute("topics", topics);
		
		return new ModelAndView("show_all_topics"); 	
	}
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@RequestMapping(value = "/addTopic", method = RequestMethod.GET)
	public ModelAndView createTopic(Map<String, Object> model){
		return new ModelAndView("add_topic_form","topic",new Topic());
		
	}
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@RequestMapping(value = "/addTopic", method = RequestMethod.POST)
	public ModelAndView createTopic(final @Valid @ModelAttribute spring.forum.models.Topic topic,
			final BindingResult result,
			final SessionStatus status,Authentication authentication){
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User usr=usersManager.getUserByEmail(userDetails.getUsername());
		topic.setAuthor(usr);
		topic.setDate(DateUtils.getCurrentDate());
		topicManager.addTopic(topic);
		
		return new ModelAndView("show_topic","post",new Post()); 	
		
	}
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "/deleteTopic/{topicID}", method = RequestMethod.GET)
	public String deleteTopic(@PathVariable String topicID){
		topicManager.deleteTopic(Integer.parseInt(topicID));
		return "welcome";
	}
}
