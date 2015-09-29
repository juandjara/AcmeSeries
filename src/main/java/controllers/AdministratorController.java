/* AdministratorController.java
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import domain.Administrator;
import forms.ActorForm;

@Controller
@RequestMapping("/admin")
public class AdministratorController extends AbstractController {
	
	// Services ---------------------------------------------------------
	@Autowired
	private AdministratorService adminService;
	
	// Listing ----------------------------------------------------------
	
	// Editing ----------------------------------------------------------
	@RequestMapping(value="/registernewadmin", method=RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		ActorForm form;
		
		form = new ActorForm();
		result = createEditModelAndView(form);
		
		return result;
	}
	
	@RequestMapping(value="/registernewadmin", method=RequestMethod.POST, params="save")
	public ModelAndView save(@ModelAttribute("actorForm") @Valid ActorForm form,
																	BindingResult binding){
		ModelAndView result;
		Administrator admin;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(form);
		}else{
			try {
				admin = adminService.reconstruct(form);
				adminService.save(admin);
				result = new ModelAndView("redirect:../security/login.do");
			} catch(IllegalArgumentException oops){
				String msg;
				if(oops.getMessage() != null && oops.getMessage().startsWith("actor")){
					msg = oops.getMessage();
				}else{
					msg = "actor.error.commit";
				}
				result = createEditModelAndView(form, msg);
			} catch (Throwable oops) {
				result = createEditModelAndView(form, "actor.error.commit");
			}
		}
		return result;
	}
	
	// Ancillary methods ------------------------------------------------
	protected ModelAndView createEditModelAndView(ActorForm form){
		ModelAndView result = createEditModelAndView(form, null);
		return result;
	}
	
	protected ModelAndView createEditModelAndView(ActorForm form, String message){
		ModelAndView result;
		
		result = new ModelAndView("admin/register");
		result.addObject("actorForm", form);
		result.addObject("message", message);
		result.addObject("requestURI", "admin/registernewadmin.do");
		
		return result;
	}
}