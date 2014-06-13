package controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
 @Controller
 
public class ShowMessage_Controller{

	@RequestMapping("/welcome")
	public ModelAndView helloWorld(){
		 
		ModelAndView model = new ModelAndView("showMessage");
		model.addObject("msg", "hello world");
 
		return model;
	}
	

}
