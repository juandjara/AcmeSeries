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

import controllers.AbstractController;

import services.FilmService;
import services.MultimediaService;
import domain.Film;

@Controller
@RequestMapping("/film/producer")
public class FilmProducerController extends AbstractController{

	// Services -----------------------------------------------------
	@Autowired
	private FilmService filmService;
	@Autowired
	private MultimediaService mediaService;
	
	// Listing ------------------------------------------------------
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required=false) String error){
		ModelAndView result;
		Collection<Film> films;
		
		films = filmService.findByProducerPrincipal();
		result = new ModelAndView("film/list");
		result.addObject("films", films);
		result.addObject("requestURI", "film/producer/list.do");
		result.addObject("message", error);
		
		return result;
	}
	
	// Editing ------------------------------------------------------
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		
		Film film = filmService.create();
		result = createEditModelAndView(film);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int filmId){
		ModelAndView result;
		String msg = null;
		
		Film film = filmService.findOne(filmId);
		try {
			Assert.notNull(film, "acme.invalid.identifier");
		} catch (IllegalArgumentException oops) {
			msg = "acme.invalid.identifier";
		}
		
		result = createEditModelAndView(film, msg);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Film film, BindingResult binding){
		ModelAndView result;
		String msg = "actor.error.commit";
		
		if(binding.hasErrors()){
			result = createEditModelAndView(film);
		}else{
			try {
				filmService.save(film);
				result = new ModelAndView("redirect: list.do");
			} catch (IllegalArgumentException oops) {
				if(oops.getMessage() != null && oops.getMessage().startsWith("acme")){
					msg = oops.getMessage();
				}
				result = createEditModelAndView(film, msg);
			} catch (Throwable oops) {
				result = createEditModelAndView(film, msg);
			}
		}
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="delete")
	public ModelAndView delete(Film film, BindingResult binding){
		ModelAndView result;
		String msg = "actor.error.commit";
		
		try {
			filmService.delete(film);
			result = new ModelAndView("redirect: list.do");
		} catch (IllegalArgumentException oops) {
			if(oops.getMessage() != null && oops.getMessage().startsWith("acme")){
				msg = oops.getMessage();
			}
			result = createEditModelAndView(film, msg);
		} catch (Throwable oops) {
			result = createEditModelAndView(film, msg);
		}
		
		return result;
	}
	
	@RequestMapping("/removetag")
	public ModelAndView removetag(@RequestParam int multimediaId,
								  @RequestParam String tag){
		ModelAndView result;
		String msg = "actor.error.commit";
		
		try{
			mediaService.removeTag(multimediaId, tag);
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
	
	// Ancillary methods --------------------------------------------
	protected ModelAndView createEditModelAndView(Film film){
		return createEditModelAndView(film, null);
	}
	
	protected ModelAndView createEditModelAndView(Film film, String message){
		ModelAndView result;
		
		result = new ModelAndView("film/edit");
		result.addObject("message", message);
		result.addObject("film", film);
		result.addObject("requestURI", "film/producer/edit.do");
		
		return result;
	}
	
}
