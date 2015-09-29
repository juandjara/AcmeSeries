package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.ContentService;
import services.CustomerService;
import domain.Comment;
import domain.Content;
import domain.Customer;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController{

	// Services -------------------------------------------------------
	@Autowired
	private CommentService commentService;
	@Autowired
	private ContentService contentService;
	@Autowired
	private CustomerService customerService;
	
	// Listing --------------------------------------------------------
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam int contentId){
		ModelAndView result;
		Content content;
		String msg = null;
		Customer principal;
		
		Collection<Comment> comments = null;
		try {
			comments = commentService.findByContent(contentId);
		} catch (IllegalArgumentException oops) {
			if(oops.getMessage()!=null && oops.getMessage().startsWith("acme")){
				msg = oops.getMessage();
			}
		}
		try{
			principal = customerService.findByPrincipal();
		}catch(Throwable oops){
			principal = null;
		}
		
		content = contentService.findOne(contentId);
		result = new ModelAndView("comment/list");
		result.addObject("comments", comments);
		result.addObject("requestURI", "comment/list.do");
		result.addObject("message", msg);
		result.addObject("content", content);
		result.addObject("principal", principal);
		
		return result;
	}
	
	@RequestMapping("/details")
	public ModelAndView details(@RequestParam int commentId){
		ModelAndView result;
		Comment comment;
		String msg = null;
		
		comment = commentService.findOne(commentId);
		try {
			Assert.notNull(comment, "acme.invalid.identifier");
		} catch (IllegalArgumentException oops) {
			msg = "acme.invalid.identifier";
		}
		
		result = new ModelAndView("comment/details");
		result.addObject("comment", comment);
		result.addObject("message", msg);
		result.addObject("requestURI", "comment/details.do");
		
		return result;
	}
	
	@RequestMapping("/replies")
	public ModelAndView replies(@RequestParam int commentId){
		ModelAndView result;
		String msg = null;
		Customer principal;
		
		Collection<Comment> comments = null;
		try {
			comments = commentService.findByParent(commentId);
		} catch (IllegalArgumentException oops) {
			if(oops.getMessage()!=null && oops.getMessage().startsWith("acme")){
				msg = oops.getMessage();
			}
		}
		try{
			principal = customerService.findByPrincipal();
		}catch(Throwable oops){
			principal = null;
		}
		
		result = new ModelAndView("comment/replies");
		result.addObject("comments", comments);
		result.addObject("requestURI", "comment/replies.do");
		result.addObject("message", msg);
		result.addObject("commentId", commentId);
		result.addObject("principal", principal);
		
		return result;
	}
	
}
