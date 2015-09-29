package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ContentService;
import services.MarkService;
import domain.Content;
import domain.Mark;

@Controller
@RequestMapping("/mark")
public class MarkController extends AbstractController{

	// Services ------------------------------------------------------
	@Autowired
	private MarkService markService;
	@Autowired
	private ContentService contentService;
	
	// Listing -------------------------------------------------------
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam int contentId){
		ModelAndView result;
		Content content;
		Collection<Mark> marks;
		String msg = null;
		Double avgScore = 0.0;
		
		content = contentService.findOne(contentId);
		try {
			Assert.notNull(content, "acme.invalid.identifier");
			marks = markService.findByContent(contentId);
			avgScore = markService.findAverageScoreByContent(content);
		} catch (IllegalArgumentException oops) {
			msg = "acme.invalid.identifier";
			marks = null;
		}
		
		result = new ModelAndView("mark/list");
		result.addObject("marks", marks);
		result.addObject("requestURI", "mark/list.do");
		result.addObject("avgScore", avgScore);
		result.addObject("message", msg);
		result.addObject("content", content);
		
		return result;
	}
	
}
