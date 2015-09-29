package controllers.customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ContentService;
import services.MarkService;
import controllers.AbstractController;
import domain.Content;
import domain.Mark;

@Controller
@RequestMapping("/mark/customer")
public class MarkCustomerController extends AbstractController{

	// Services --------------------------------------------------------
	
	// Listing ---------------------------------------------------------
	@Autowired
	private MarkService markService;
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/list")
	public ModelAndView list(){
		ModelAndView result;
		Collection<Mark> marks;
		
		Map<Integer, Content> contentMap;
		contentMap = new HashMap<Integer, Content>();
		
		marks = markService.findViewedByCustomerPrincipal();
		for(Mark m:marks){
			Content content = getContent(m.getId());
			contentMap.put(m.getId(), content);
		}
		result = new ModelAndView("mark/list2");
		result.addObject("marks", marks);
		result.addObject("requestURI", "mark/customer/list.do");
		result.addObject("contentMap", contentMap);
		
		return result;
	}
	
	// Editing -------------------------------------------------------s
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView createOrEdit(@RequestParam int contentId){
		ModelAndView result;
		String msg = null;
		Mark mark;
		
		try{
			mark = markService.createOrFind(contentId);
		}catch(IllegalArgumentException oops){
			msg = "acme.invalid.identifier";
			mark = null;
		}
		
		result = createEditModelAndView(mark, msg);
		result.addObject("controller", this);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Mark mark, BindingResult binding){
		ModelAndView result;
		String msg = "actor.error.commit";
		int contentId = mark.getContent().getId();
		String url = "../list.do?contentId="+contentId;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(mark);
		}else{
			try {
				markService.save(mark);
				result = new ModelAndView("redirect:"+url);
			} catch (IllegalArgumentException oops) {
				if(oops.getMessage() != null && oops.getMessage().startsWith("acme")){
					msg = oops.getMessage();
				}
				result = createEditModelAndView(mark, msg);
			} catch (Throwable oops) {
				result = createEditModelAndView(mark, msg);
			}
		}
		
		return result;
	}
	
	// Ancillary methods --------------------------------------------
	protected ModelAndView createEditModelAndView(Mark mark){
		return createEditModelAndView(mark, null);
	}
	
	protected ModelAndView createEditModelAndView(Mark mark, String message){
		ModelAndView result;
		
		result = new ModelAndView("mark/edit");
		result.addObject("message", message);
		result.addObject("mark", mark);
		result.addObject("requestURI", "mark/customer/edit.do");
		
		return result;
	}
	
	@Transactional
	public Content getContent(int markId){
		return contentService.findByMark(markId);
	}
}
