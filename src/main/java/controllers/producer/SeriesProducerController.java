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

import controllers.AbstractController;

import services.TVSerieService;
import services.MultimediaService;
import domain.TVSerie;

@Controller
@RequestMapping("/series/producer")
public class SeriesProducerController extends AbstractController{

	// Services -----------------------------------------------------
	@Autowired
	private TVSerieService seriesService;
	@Autowired
	private MultimediaService mediaService;
	
	// Listing ------------------------------------------------------
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required=false) String error){
		ModelAndView result;
		Collection<TVSerie> series;
		
		series = seriesService.findByProducerPrincipal();
		result = new ModelAndView("series/list");
		result.addObject("series", series);
		result.addObject("requestURI", "series/producer/list.do");
		result.addObject("message", error);
		
		return result;
	}
	
	// Editing ------------------------------------------------------
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		
		TVSerie serie = seriesService.create();
		result = createEditModelAndView(serie);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int serieId){
		ModelAndView result;
		String msg = null;
		
		TVSerie serie = seriesService.findOne(serieId);
		try {
			Assert.notNull(serie, "acme.invalid.identifier");
		} catch (IllegalArgumentException oops) {
			msg = "acme.invalid.identifier";
		}
		
		result = createEditModelAndView(serie, msg);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@ModelAttribute("serie") 
							 @Valid TVSerie serie, 
									BindingResult binding){
		ModelAndView result;
		String msg = "actor.error.commit";
		
		if(binding.hasErrors()){
			result = createEditModelAndView(serie);
		}else{
			try {
				seriesService.save(serie);
				result = new ModelAndView("redirect: list.do");
			} catch (IllegalArgumentException oops) {
				if(oops.getMessage() != null && oops.getMessage().startsWith("acme")){
					msg = oops.getMessage();
				}
				result = createEditModelAndView(serie, msg);
			} catch (Throwable oops) {
				result = createEditModelAndView(serie, msg);
			}
		}
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="delete")
	public ModelAndView delete(TVSerie serie, BindingResult binding){
		ModelAndView result;
		String msg = "actor.error.commit";
		
		try {
			seriesService.delete(serie);
			result = new ModelAndView("redirect: list.do");
		} catch (IllegalArgumentException oops) {
			if(oops.getMessage() != null && oops.getMessage().startsWith("acme")){
				msg = oops.getMessage();
			}
			result = createEditModelAndView(serie, msg);
		} catch (Throwable oops) {
			result = createEditModelAndView(serie, msg);
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
	protected ModelAndView createEditModelAndView(TVSerie serie){
		return createEditModelAndView(serie, null);
	}
	
	protected ModelAndView createEditModelAndView(TVSerie serie, String message){
		ModelAndView result;
		
		result = new ModelAndView("series/edit");
		result.addObject("message", message);
		result.addObject("serie", serie);
		result.addObject("requestURI", "series/producer/edit.do");
		
		return result;
	}
	
}
