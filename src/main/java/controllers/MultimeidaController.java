package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.MarkService;
import services.MultimediaService;
import domain.Content;
import domain.Multimedia;

@Controller
@RequestMapping("/media")
public class MultimeidaController extends AbstractController{

	// Services -------------------------------------------------------
	@Autowired
	private MultimediaService mediaService;
	@Autowired
	private MarkService markService;
	
	// Listing --------------------------------------------------------
	@RequestMapping("/search")
	public ModelAndView search(){
		ModelAndView result;
		
		result = new ModelAndView("media/search");
		result.addObject("requestURI", "media/search.do");
		
		return result;
	}
	
	@RequestMapping("/query")
	public @ResponseBody ModelAndView search(@RequestParam String key){
		ModelAndView result;
		Collection<Multimedia> items;
		
		items = mediaService.findByKeyword(key);
		result = new ModelAndView("media/searchresult");
		result.addObject("items", items);
		result.addObject("requestURI", "media/search.do");
		result.addObject("controller", this);
		
		return result;
	}
	
	// Ancillary methods
	public Double avgScore(Content content){
		return markService.findAverageScoreByContent(content);
	}
	
}
