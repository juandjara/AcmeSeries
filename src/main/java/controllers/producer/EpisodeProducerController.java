package controllers.producer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EpisodeService;
import services.TVSerieService;
import controllers.AbstractController;
import domain.Episode;
import domain.TVSerie;

@Controller
@RequestMapping("/episode/producer")
public class EpisodeProducerController extends AbstractController{

	// Services ------------------------------------------------------
	@Autowired
	private EpisodeService episodeService;
	@Autowired
	private TVSerieService serieService;
	
	// Editing -------------------------------------------------------
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create(@RequestParam int serieId){
		ModelAndView result;
		String msg = null;
		Episode episode;
		
		TVSerie serie = serieService.findOne(serieId);
		try {
			Assert.notNull(serie, "acme.invalid.identifier");
			episode = episodeService.create(serie);
		} catch (IllegalArgumentException oops) {
			msg = "acme.invalid.identifier";
			episode = null;
		}
		
		result = createEditModelAndView(episode, msg);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int episodeId){
		ModelAndView result;
		String msg = null;
		Episode episode = episodeService.findOne(episodeId);
		
		try {
			Assert.notNull(episode, "acme.invalid.identifier");
		} catch (IllegalArgumentException oops) {
			msg="acme.invalid.identifier";
		}
		
		result = createEditModelAndView(episode, msg);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Episode episode, BindingResult binding){
		ModelAndView result;
		String msg = "actor.error.commit";
		int serieId = episode.getSerie().getId();
		String url = "../list.do?serieId="+serieId;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(episode);
		}else{
			try {
				episodeService.save(episode);
				result = new ModelAndView("redirect:"+url);
			} catch (IllegalArgumentException oops) {
				if(oops.getMessage() != null && oops.getMessage().startsWith("acme")){
					msg = oops.getMessage();
				}
				result = createEditModelAndView(episode, msg);
			} catch (Throwable oops) {
				result = createEditModelAndView(episode, msg);
			}
		}
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="delete")
	public ModelAndView delete(Episode episode, BindingResult binding){
		ModelAndView result;
		String msg = "actor.error.commit";
		int serieId = episode.getSerie().getId();
		String url = "../list.do?serieId="+serieId;
		
		try {
			episodeService.delete(episode);
			result = new ModelAndView("redirect:"+url);
		} catch (IllegalArgumentException oops) {
			if(oops.getMessage() != null && oops.getMessage().startsWith("acme")){
				msg = oops.getMessage();
			}
			result = createEditModelAndView(episode, msg);
		} catch (Throwable oops) {
			result = createEditModelAndView(episode, msg);
		}
		
		return result;
	}
	
	// Ancillary methods --------------------------------------------
	protected ModelAndView createEditModelAndView(Episode episode){
		return createEditModelAndView(episode, null);
	}
	
	protected ModelAndView createEditModelAndView(Episode episode, String message){
		ModelAndView result;
		
		result = new ModelAndView("episode/edit");
		result.addObject("message", message);
		result.addObject("episode", episode);
		if (episode != null) {
			result.addObject("serieId", episode.getSerie().getId());
		}
		result.addObject("requestURI", "episode/producer/edit.do");
		
		return result;
	}
	
}
