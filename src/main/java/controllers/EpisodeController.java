package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EpisodeService;
import services.MultimediaService;
import domain.Episode;
import domain.Multimedia;

@Controller
@RequestMapping("/episode")
public class EpisodeController extends AbstractController{
	
	// Services -----------------------------------------------------
	@Autowired 
	private EpisodeService episodeService;
	@Autowired
	private MultimediaService mediaService;
	
	// Listing ------------------------------------------------------
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam int serieId){
		ModelAndView result;
		Collection<Episode> episodes;
		Boolean isPrincipal = false;
		Multimedia media;
		String msg = null;
		
		media = mediaService.findOne(serieId);
		try {
			Assert.notNull(media, "acme.invalid.identifier");
			episodes = episodeService.findByTVSerie(serieId);
		} catch (Throwable oops) {
			msg = "acme.invalid.identifier";
			episodes = null;
		}
		
		try {
			mediaService.checkPrincipal(media);
			isPrincipal = true;
		} catch (Throwable oops) {
			isPrincipal = false;
		}
		
		result = new ModelAndView("episode/list");
		result.addObject("episodes", episodes);
		result.addObject("serieId", serieId);
		result.addObject("requestURI", "episode/list.do");
		result.addObject("message", msg);
		result.addObject("isPrincipal", isPrincipal);
		
		return result;
	}
	
	@RequestMapping("/details")
	public ModelAndView details(@RequestParam int episodeId){
		ModelAndView result;
		Episode episode;
		
		episode = episodeService.findOne(episodeId);
		result = new ModelAndView("episode/details");
		result.addObject("episode", episode);
		result.addObject("requestURI", "episode/details.do");
		
		return result;
	}
	
	// Ancillary methods
	
}
