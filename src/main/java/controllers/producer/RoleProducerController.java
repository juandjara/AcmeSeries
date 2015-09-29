package controllers.producer;

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

import services.RoleService;
import services.StaffService;
import controllers.AbstractController;
import domain.Role;
import domain.Staff;
import forms.RoleForm;

@Controller
@RequestMapping("/role/producer")
public class RoleProducerController extends AbstractController{

	// Services -------------------------------------------------------
	@Autowired
	private RoleService roleService;
	@Autowired
	private StaffService staffService;
	
	// Editing ---------------------------------------------------------
	@RequestMapping("/create")
	public ModelAndView create(@RequestParam int mediaId){
		ModelAndView result;
		RoleForm roleForm;
		
		roleForm = new RoleForm();
		roleForm.setMediaId(mediaId);
		result = createEditModelAndView(roleForm);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@ModelAttribute("roleForm") @Valid RoleForm form,
																BindingResult binding){
		ModelAndView result;
		String msg = "actor.error.commit";
		int mediaId = form.getMediaId();
		String url = "../list.do?mediaId="+mediaId;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(form);
		}else{
			try {
				Role role = roleService.reconstruct(form);
				roleService.save(role);
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
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int roleId){
		ModelAndView result;
		String msg = null;
		Role role = roleService.findOne(roleId);
		
		try {
			Assert.notNull(role, "acme.invalid.identifier");
		} catch (IllegalArgumentException oops) {
			msg="acme.invalid.identifier";
		}
		
		RoleForm form = roleService.construct(role);
		result = createEditModelAndView(form, msg);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="delete")
	public ModelAndView delete(@Valid RoleForm form, BindingResult binding){
		ModelAndView result;
		String msg = "actor.error.commit";
		int mediaId = form.getMediaId();
		String url = "../list.do?mediaId="+mediaId;
		
		try {
			Role role = roleService.reconstruct(form);
			roleService.delete(role);
			result = new ModelAndView("redirect:"+url);
		} catch (IllegalArgumentException oops) {
			if(oops.getMessage() != null && oops.getMessage().startsWith("acme")){
				msg = oops.getMessage();
			}
			result = createEditModelAndView(form, msg);
		} catch (Throwable oops) {
			result = createEditModelAndView(form, msg);
		}
		
		return result;
	}
	
	// Ancillary methods --------------------------------------------
	protected ModelAndView createEditModelAndView(RoleForm roleForm){
		return createEditModelAndView(roleForm, null);
	}
	
	protected ModelAndView createEditModelAndView(RoleForm roleForm, String message){
		ModelAndView result;
		
		Collection<Staff> staffmembers;
		staffmembers = staffService.findByProducerPrincipal();
		
		result = new ModelAndView("role/edit");
		result.addObject("message", message);
		result.addObject("roleForm", roleForm);
		result.addObject("requestURI", "role/producer/edit.do");
		result.addObject("staffmembers", staffmembers);
		
		return result;
	}
}
