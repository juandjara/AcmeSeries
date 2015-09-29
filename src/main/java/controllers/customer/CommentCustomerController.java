package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.CustomerService;
import controllers.AbstractController;
import domain.Comment;
import domain.Customer;
import forms.CommentForm;

@Controller
@RequestMapping("/comment/customer")
public class CommentCustomerController extends AbstractController{

	// Services 
	@Autowired
	private CommentService commentService;
	@Autowired
	private CustomerService customerService;
	
	// Listing
	@RequestMapping("/inbox")
	public ModelAndView inbox(){
		ModelAndView result;
		String msg = null;
		
		Collection<Comment> comments = null;
		try {
			comments = commentService.findByReceiverPrincipal();
		} catch (IllegalArgumentException oops) {
			if(oops.getMessage()!=null && oops.getMessage().startsWith("acme")){
				msg = oops.getMessage();
			}
		} catch (Throwable oops){
			msg = "acme.error.commit";
		}
		
		result = new ModelAndView("comment/inbox");
		result.addObject("comments", comments);
		result.addObject("requestURI", "comment/customer/inbox.do");
		result.addObject("message", msg);
		
		return result;
	}
	
	@RequestMapping("/outbox")
	public ModelAndView outbox(@RequestParam(required=false) String error){
		ModelAndView result;
		String msg = null;
		Customer principal;
		
		Collection<Comment> comments = null;
		try {
			comments = commentService.findBySenderPrincipal();
		} catch (IllegalArgumentException oops) {
			if(oops.getMessage()!=null && oops.getMessage().startsWith("acme")){
				msg = oops.getMessage();
			}
		} catch (Throwable oops){
			msg = "acme.error.commit";
		} finally {
			if(msg == null){
				msg = error;
			}
		}
		try{
			principal = customerService.findByPrincipal();
		}catch(Throwable oops){
			principal = null;
		}
		
		result = new ModelAndView("comment/outbox");
		result.addObject("comments", comments);
		result.addObject("requestURI", "comment/customer/outbox.do");
		result.addObject("message", msg);
		result.addObject("principal", principal);
		
		return result;
	}
	
	// Editing 
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create(@RequestParam int contentId){
		ModelAndView result;
		CommentForm form;
		
		form = new CommentForm();
		form.setContentId(contentId);
		result = createEditModelAndView(form);
		
		return result;
	}
	
	@RequestMapping(value="/reply", method=RequestMethod.GET)
	public ModelAndView reply(@RequestParam int parentId){
		ModelAndView result;
		CommentForm form;
		Comment parent;
		String msg = null;
		int contentId = 0;
		
		parent = commentService.findOne(parentId);
		try {
			Assert.notNull(parent, "acme.invalid.identifier");
			contentId = parent.getContent().getId();
		} catch (Throwable oops) {
			msg = "acme.invalid.identifier";
		}
		
		form = new CommentForm();
		form.setParentId(parentId);
		form.setContentId(contentId);
		result = createEditModelAndView(form, msg);
		
		return result;
	}
	
	@RequestMapping("/recommend")
	public ModelAndView recommend(@RequestParam int contentId){
		ModelAndView result;
		CommentForm form;
		
		form = new CommentForm();
		form.setContentId(contentId);
		// use this for checking at save if this is a recommendation
		form.setReceiverId(-1); 
		result = createEditModelAndView(form);
		
		return result;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST, params="save")
	public ModelAndView save(@ModelAttribute("commentForm") 
							 @Valid CommentForm form, 
							 		BindingResult binding){
		ModelAndView result;
		String msg = "actor.error.commit";
		int contentId = form.getContentId();
		String url = "../list.do?contentId="+contentId;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(form);
		}else{
			try {
				Comment comment = commentService.reconstruct(form);
				commentService.save(comment);
				result = new ModelAndView("redirect:"+url);
			} catch (IllegalArgumentException oops) {
				if(oops.getMessage() != null && oops.getMessage().startsWith("acme")){
					msg = oops.getMessage();
				}
				result = createEditModelAndView(form, msg);
			} catch (Throwable oops) {
				result = createEditModelAndView(form, msg);
			}
		}
		
		return result;
	}
	
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam int commentId){
		ModelAndView result;
		String msg = "actor.error.commit";
		String url = "outbox.do";
		try{
			Comment comment = commentService.findOne(commentId);
			Assert.notNull(comment, "acme.invalid.identifier");
			commentService.delete(comment);
			result = new ModelAndView("redirect:"+url);
		}catch(IllegalArgumentException oops){
			if(oops.getMessage() != null && oops.getMessage().startsWith("acme")){
				msg = oops.getMessage();
			}
			result = new ModelAndView("redirect:"+url+"?error="+msg);
		}catch(Throwable oops){
			result = new ModelAndView("redirect:"+url+"?error="+msg);
		}
		
		return result;
	}
	
	// Ancillary methods --------------------------------------------
	protected ModelAndView createEditModelAndView(CommentForm form){
		return createEditModelAndView(form, null);
	}
	
	protected ModelAndView createEditModelAndView(CommentForm form, String message){
		ModelAndView result;
		Customer principal;
		Collection<Customer> customers;
		String uri = "comment/customer/create.do";
		
		principal = customerService.findByPrincipal();
		customers = customerService.findAll();
		customers.remove(principal);
		
		result = new ModelAndView("comment/edit");
		result.addObject("message", message);
		result.addObject("commentForm", form);
		result.addObject("customers", customers);
		result.addObject("requestURI", uri);
		if(form.getReceiverId() != 0){
			result.addObject("isRecommendation", true);
		}
		
		return result;
	}
}
