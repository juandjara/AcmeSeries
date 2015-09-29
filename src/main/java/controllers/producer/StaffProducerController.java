package controllers.producer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.StaffService;
import controllers.AbstractController;
import domain.Staff;

@Controller
@RequestMapping("/staff/producer")
public class StaffProducerController extends AbstractController{

	// Services -----------------------------------------------------
	@Autowired
	private StaffService staffService;
	
	// Listing ------------------------------------------------------
	@RequestMapping("/list")
	public ModelAndView list(){
		ModelAndView result;
		Collection<Staff> staff;
		
		staff = staffService.findByProducerPrincipal();
		result = new ModelAndView("staff/list");
		result.addObject("staff", staff);
		result.addObject("requestURI", "series/producer/list.do");
		
		return result;
	}
	
	// Editing -------------------------------------------------------s
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		
		Staff staff = staffService.create();
		result = createEditModelAndView(staff);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int staffId){
		ModelAndView result;
		String msg = null;
		
		Staff staff = staffService.findOne(staffId);
		try {
			Assert.notNull(staff, "acme.invalid.identifier");
		} catch (IllegalArgumentException oops) {
			msg = "acme.invalid.identifier";
		}
		
		result = createEditModelAndView(staff, msg);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Staff staff, BindingResult binding){
		ModelAndView result;
		String msg = "actor.error.commit";
		
		if(binding.hasErrors()){
			result = createEditModelAndView(staff);
		}else{
			try {
				staffService.save(staff);
				result = new ModelAndView("redirect: list.do");
			} catch (IllegalArgumentException oops) {
				if(oops.getMessage() != null && oops.getMessage().startsWith("acme")){
					msg = oops.getMessage();
				}
				result = createEditModelAndView(staff, msg);
			} catch (Throwable oops) {
				result = createEditModelAndView(staff, msg);
			}
		}
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="delete")
	public ModelAndView delete(Staff staff, BindingResult binding){
		ModelAndView result;
		String msg = "actor.error.commit";
		
		try {
			staffService.delete(staff);
			result = new ModelAndView("redirect: list.do");
		} catch (IllegalArgumentException oops) {
			if(oops.getMessage() != null && oops.getMessage().startsWith("acme")){
				msg = oops.getMessage();
			}
			result = createEditModelAndView(staff, msg);
		} catch (Throwable oops) {
			result = createEditModelAndView(staff, msg);
		}
		
		return result;
	}
	
	// Ancillary methods --------------------------------------------
	protected ModelAndView createEditModelAndView(Staff staff){
		return createEditModelAndView(staff, null);
	}
	
	protected ModelAndView createEditModelAndView(Staff staff, String message){
		ModelAndView result;
		
		result = new ModelAndView("staff/edit");
		result.addObject("message", message);
		result.addObject("staff", staff);
		result.addObject("requestURI", "staff/producer/edit.do");
		
		return result;
	}
}
