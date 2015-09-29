package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FilmService;
import domain.Film;

@Controller
@RequestMapping("/film")
public class FilmController extends AbstractController{
	
	// Services -----------------------------------------------------
	@Autowired
	private FilmService filmService;
	
	// Listing ------------------------------------------------------
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required=false) String error){
		ModelAndView result;
		Collection<Film> films;
		
		films = filmService.findAll();
		result = new ModelAndView("film/list");
		result.addObject("films", films);
		result.addObject("requestURI", "film/list.do");
		result.addObject("message", error);
		
		return result;
	}
	
	@RequestMapping("/details")
	public ModelAndView details(@RequestParam int filmId){
		ModelAndView result;
		Film film;
		
		film = filmService.findOne(filmId);
		result = new ModelAndView("film/details");
		result.addObject("film", film);
		result.addObject("requestURI", "film/details.do");
		
		return result;
	}
	
	// Ancillary methods
	
}
