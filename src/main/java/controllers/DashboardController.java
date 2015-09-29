package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.ContentService;
import services.CustomerService;
import services.EpisodeService;
import services.FilmService;
import services.ProducerCompanyService;
import services.TVSerieService;
import domain.Comment;
import domain.Content;
import domain.Customer;
import domain.Episode;
import domain.Film;
import domain.ProducerCompany;
import domain.TVSerie;

@Controller
@RequestMapping("/dashboard")
public class DashboardController extends AbstractController{

	// Services --------------------------------------------------------
	@Autowired
	private EpisodeService episodeService;
	@Autowired
	private FilmService filmService;
	@Autowired
	private TVSerieService serieService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ProducerCompanyService producerService;
	@Autowired
	private ContentService contentService;
	
	// Listing ---------------------------------------------------------
	
	/** Generic Dashboard */
	@RequestMapping("/generic")
	public ModelAndView generic(){
		ModelAndView result;
		
		Collection<Episode> mostViewedEpisodes = 
				episodeService.findMostViewed();
		Collection<Film> mostViewedFilms = 
				filmService.findMostViewed();
		Collection<TVSerie> mostViewedSeries = 
				serieService.findMostViewed();
		
		Collection<Episode> betterAvgEpisodes = 
				episodeService.findByBetterAverageScore();
		Collection<TVSerie> betterAvgSeries = 
				serieService.findByBetterAverageScore();
		Collection<Film> betterAvgFilms = 
				filmService.findByBetterAverageScore();
		
		Collection<Episode> mostCommentedEpisodes = 
				episodeService.findMostCommented();
		Collection<TVSerie> mostCommentedSeries =
				serieService.findMostCommented();
		Collection<Film> mostCommentedFilms = 
				filmService.findMostCommented();
		
		result = new ModelAndView("dashboard/generic");
		result.addObject("requestURI", "dashboard/generic.do");
		result.addObject("mostViewedEpisodes", mostViewedEpisodes);
		result.addObject("mostViewedSeries", mostViewedSeries);
		result.addObject("mostViewedFilms", mostViewedFilms);
		
		result.addObject("betterAvgEpisodes", betterAvgEpisodes);
		result.addObject("betterAvgSeries", betterAvgSeries);
		result.addObject("betterAvgFilms", betterAvgFilms);
		
		result.addObject("mostCommentedEpisodes", mostCommentedEpisodes);
		result.addObject("mostCommentedSeries", mostCommentedSeries);
		result.addObject("mostCommentedFilms", mostCommentedFilms);
		
		return result;
	}
	
	/** Customer dashboard */
	@RequestMapping("/customer")
	public ModelAndView customer(){
		ModelAndView result;
		Customer principal = customerService.findByPrincipal();
		
		int nOfComments = 
			commentService.numberOfcommentsByPrincipalCustomer();
		Long nOfFilms = filmService.CountViewedByCustomer(principal);
		Long nOfSeries = serieService.CountViewedByCustomer(principal);
		Long nOfEpisodes = episodeService.CountViewedByCustomer(principal);
		Collection<Comment> moreReplies = 
			commentService.commentsWithMoreRepliesByCustomerPrincipal();
		
		result = new ModelAndView("dashboard/customer");
		result.addObject("requestURI", "dashboard/customer.do");
		result.addObject("nOfComments", nOfComments);
		result.addObject("nOfFilms", nOfFilms);
		result.addObject("nOfEpisodes", nOfEpisodes);
		result.addObject("nOfSeries", nOfSeries);
		result.addObject("moreReplies", moreReplies);
		return result;
	}
	
	/** Producer dahsboard */
	@RequestMapping("/producer")
	public ModelAndView producer(){
		ModelAndView result;
		ProducerCompany principal;
		
		principal = producerService.findByPrincipal();
		Long nOfCustomers = 
			customerService.countByContentViewedOfCompany(principal);
		Collection<Content> mostCommentedContent = 
			contentService.FindMostCommentedByProducerCompany(principal);
		Collection<Content> betterAvgContent =
			contentService.findByBetterAvgScoreAndProducerPrincipal();
		
		result = new ModelAndView("dashboard/producer");
		result.addObject("requestURI", "dashboard/producer.do");
		result.addObject("nOfCustomers", nOfCustomers);
		result.addObject("mostCommentedContents", mostCommentedContent);
		result.addObject("betterAvgContents", betterAvgContent);
		
		return result;
	}
	
	/** Admin dashboard */
	@RequestMapping("/admin")
	public ModelAndView admin(){
		ModelAndView result;
		
		Collection<ProducerCompany> producersBetterScore = 
			producerService.findByBestRatedMultimedia();
		Collection<ProducerCompany> producersWorstScore = 
			producerService.findByWorstRatedMultimedia();
		Collection<ProducerCompany> producersMoreMedia =
			producerService.findByMoreMultimediaCreated();
		Collection<Customer> customersMoreComments =
			customerService.findByMoreCommentsCreated();
		Collection<Customer> customersMoreEpisodes =
			customerService.findByMoreEpisodesViewed();
		Collection<Customer> customersMoreFilms =
			customerService.findByMoreFilmsViewed();
		
		result = new ModelAndView("dashboard/admin");
		result.addObject("requestURI", "dashboard/admin.do");
		result.addObject("producersBetterScore", producersBetterScore);
		result.addObject("producersWorstScore", producersWorstScore);
		result.addObject("producersMoreMedia", producersMoreMedia);
		result.addObject("customersMoreComments", customersMoreComments);
		result.addObject("customersMoreEpisodes", customersMoreEpisodes);
		result.addObject("customersMoreFilms", customersMoreFilms);
		
		return result;
	}
	
}
