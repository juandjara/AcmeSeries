/* ProducerCompanyController.java
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

import services.ProducerCompanyService;
import domain.ProducerCompany;
import forms.ProducerCompanyForm;

@Controller
@RequestMapping("/producer")
public class ProducerCompanyController extends AbstractController {

	// Services ---------------------------------------------------------
	@Autowired
	private ProducerCompanyService producerService;
	
	// Listing ----------------------------------------------------------
	
	// Editing ----------------------------------------------------------
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		ProducerCompanyForm form;
		
		form = new ProducerCompanyForm();
		result = createEditModelAndView(form);
		
		return result;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST, params="save")
	public ModelAndView save(@ModelAttribute("producerForm") @Valid ProducerCompanyForm form,
																	BindingResult binding){
		ModelAndView result;
		ProducerCompany producer;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(form);
		}else{
			try {
				producer = producerService.reconstruct(form);
				producerService.save(producer);
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
	protected ModelAndView createEditModelAndView(ProducerCompanyForm form){
		ModelAndView result = createEditModelAndView(form, null);
		return result;
	}
	
	protected ModelAndView createEditModelAndView(ProducerCompanyForm form, String message){
		ModelAndView result;
		
		result = new ModelAndView("producer/register");
		result.addObject("producerForm", form);
		result.addObject("message", message);
		result.addObject("requestURI", "producer/register.do");
		
		return result;
	}
}