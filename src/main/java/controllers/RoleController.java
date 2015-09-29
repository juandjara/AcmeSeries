package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MultimediaService;
import services.RoleService;
import domain.Multimedia;
import domain.Role;

@Controller
@RequestMapping("/role")
public class RoleController extends AbstractController{

	// Services --------------------------------------------------------
	@Autowired
	private RoleService roleService;
	@Autowired
	private MultimediaService mediaService;

	// Listing ---------------------------------------------------------
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam int mediaId){
		ModelAndView result;
		Collection<Role> roles;
		Multimedia media;
		Boolean isPrincipal = false;
		String msg = null;
		
		media = mediaService.findOne(mediaId);
		try {
			Assert.notNull(media, "acme.invalid.identifier");
			roles = roleService.findByMultimedia(media);
		} catch (IllegalArgumentException oops) {
			msg = "acme.invalid.identifier";
			roles = null;
		}
		
		try {
			mediaService.checkPrincipal(media);
			isPrincipal = true;
		} catch (Throwable oops) {
			isPrincipal = false;
		}
		
		result = new ModelAndView("role/list");
		result.addObject("roles", roles);
		result.addObject("requestURI", "role/list.do");
		result.addObject("media", media);
		result.addObject("message", msg);
		result.addObject("isPrincipal", isPrincipal);
		
		return result;
	}
	
}
