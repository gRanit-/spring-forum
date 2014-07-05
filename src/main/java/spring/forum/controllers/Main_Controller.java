package spring.forum.controllers;



import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import spring.forum.models.Post;
import spring.forum.models.Topic;
import spring.forum.models.User;
import spring.forum.models.UserRole;
import spring.forum.services.PostsManager;
import spring.forum.services.TopicsManager;
import spring.forum.services.UsersManager;
import spring.forum.services.UserRoleManager;
@Controller
public class Main_Controller {
	@Autowired
	private UsersManager usersManager;
	@Autowired
	private	 UserRoleManager roleManager;
	@Autowired
	private	 TopicsManager topicManager;
	@Autowired
	private	 PostsManager postsManager;
	

	
	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView model = new ModelAndView();
		model.setViewName("admin");

		return model;

	}
	
	
}
