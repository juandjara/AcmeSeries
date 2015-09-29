package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import controllers.AbstractController;
import domain.Customer;

@Controller
@RequestMapping("/customer/admin")
public class CustomerAdministratorController extends AbstractController{

	// Services ---------------------------------------------------------
	@Autowired
	private CustomerService customerService;
	
	// Listing ----------------------------------------------------------
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required=false) String error){
		ModelAndView result;
		Collection<Customer> customers;
		
		customers = customerService.findAll();
		result = new ModelAndView("customer/list");
		result.addObject("message", error);
		result.addObject("customers", customers);
		result.addObject("requestURI", "customer/admin/list.do");
		
		return result;
	}
	
	// Editing ------------------------------------------------------
	@RequestMapping("toggleban")
	public ModelAndView toggleban(@RequestParam int customerId){
		ModelAndView result;
		String msg = "actor.error.commit";
		
		try{
			customerService.toggleBan(customerId);
			result = new ModelAndView("redirect: list.do");
		}catch(IllegalArgumentException oops){
			if(oops.getMessage() != null && oops.getMessage().startsWith("acme")){
				msg = oops.getMessage();
			}
			result = new ModelAndView("redirect: list.do?error="+msg);
		}catch(Throwable oops){
			result = new ModelAndView("redirect: list.do?error="+msg);
		}
		
		return result;
	}
	
	// Ancillary methods ------------------------------------------------

}
