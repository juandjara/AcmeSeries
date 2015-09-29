package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TVSerieService;
import domain.TVSerie;

@Controller
@RequestMapping("/series")
public class SeriesController extends AbstractController{
	
	// Services -----------------------------------------------------
	@Autowired
	private TVSerieService seriesService;
	
	// Listing ------------------------------------------------------
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required=false) String error){
		ModelAndView result;
		Collection<TVSerie> series;
		
		series = seriesService.findAll();
		result = new ModelAndView("series/list");
		result.addObject("series", series);
		result.addObject("requestURI", "series/list.do");
		result.addObject("message", error);
		
		return result;
	}
	
	@RequestMapping("/details")
	public ModelAndView details(@RequestParam int serieId){
		ModelAndView result;
		TVSerie serie;
		
		serie = seriesService.findOne(serieId);
		result = new ModelAndView("series/details");
		result.addObject("serie", serie);
		result.addObject("requestURI", "series/details.do");
		
		return result;
	}
	
	// Ancillary methods
	
}
