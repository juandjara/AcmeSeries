package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import utils.AbstractTest;
import domain.Comment;
import domain.Content;
import domain.Customer;
import domain.Episode;
import domain.Film;
import domain.ProducerCompany;
import domain.TVSerie;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={	
                "classpath:spring/datasource.xml",
                "classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class DashboardTest extends AbstractTest {

	// Service under test -------------------------------------------
	
	// Supporting services ------------------------------------------
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
	
	// Test cases ---------------------------------------------------
	
	/** REQ21. Positive test case.
	 * A user who is authenticated as a customer must be able to
	 * display a dashboard with information relevant to customers */
	@Test
	public void customerDashboardTest(){
		authenticate("customer");
		customerDashboard();
	}
	
	/** REQ21. Negative test case.
	 * We will try to display this dashboard using a wrong authentication
	 * and we will catch the exception */
	@Test(expected=IllegalArgumentException.class)
	public void customerDashboardTestNegative(){
		authenticate("producer");
		customerDashboard();
	}
	
	/** REQ30. Positive test case.
	 * A user who is authenticated as a producer company must be able to
	 * display a dashboard with information relevant to producers */
	@Test
	public void producerDashboardTest(){
		authenticate("producer");
		producerDashboard();
	}
	
	/** REQ30. Negative test case.
	 * We will try to display this dashboard using a wrong authentication
	 * and we will catch the exception */
	@Test(expected=IllegalArgumentException.class)
	public void producerDashboardTestNegative(){
		authenticate("customer");
		producerDashboard();
	}
	
	/** REQ33. Positive test case.
	 * A user who is authenticated as a administrator must be able to
	 * display a dashboard with information relevant to administrators */
	@Test
	public void adminDashboardTest(){
		authenticate("admin");
		adminDashboard();
	}
	
	/** REQ33. Negative test case.
	 * We will try to display this dashboard using a wrong authentication
	 * and we will catch the exception */
	@Test(expected=IllegalArgumentException.class)
	public void adminDashboardTestNegative(){
		authenticate("producer");
		adminDashboard();
	}
	
	/** REQ11. Positive test case.
	 * Every authenticated  must be able to
	 * display a dashboard with information relevant to the average user */
	@Test
	public void genericDashboardTest(){
		authenticate("customer");
		genericDashboard();
	}
	
	/** REQ11. Negative test case.
	 * We will try to display this dashboard using a wrong authentication
	 * and we will catch the exception */
	@Test(expected=IllegalArgumentException.class)
	public void genericDashboardTestNegative(){
		authenticate(null);
		genericDashboard();
	}
	
	// Ancillary methods --------------------------------------------
	public void genericDashboard(){
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
		
		// The following asserts refer to the actual information
		// displayed on the various tables when the BD is filled
		// with the data of populatedatabase.xml
		
		Assert.isTrue(mostViewedEpisodes.size() == 2);
		Assert.isTrue(mostCommentedEpisodes.size() == 3);
		Assert.isTrue(betterAvgEpisodes.size() == 1);
		Assert.isTrue(mostViewedSeries.size() == 1);
		Assert.isTrue(mostCommentedSeries.size() == 2);
		Assert.isTrue(betterAvgSeries.size() == 1);
		Assert.isTrue(mostViewedFilms.size() == 1);
		Assert.isTrue(mostCommentedFilms.size() == 1);
		Assert.isTrue(betterAvgFilms.size() == 1);
	}
	
	public void customerDashboard(){
		Customer principal = customerService.findByPrincipal();
		
		int nOfComments = 
			commentService.numberOfcommentsByPrincipalCustomer();
		Long nOfFilms = filmService.CountViewedByCustomer(principal);
		Long nOfSeries = serieService.CountViewedByCustomer(principal);
		Long nOfEpisodes = episodeService.CountViewedByCustomer(principal);
		Collection<Comment> moreReplies = 
			commentService.commentsWithMoreRepliesByCustomerPrincipal();
		
		// The following asserts refer to the actual information
		// displayed on the various tables when the BD is filled
		// with the data of populatedatabase.xml
		
		Assert.isTrue(nOfComments == 2);
		Assert.isTrue(nOfFilms == 2);
		Assert.isTrue(nOfSeries == 1);
		Assert.isTrue(nOfEpisodes == 0);
		Assert.isTrue(moreReplies.size() == 1);
	}
	
	public void producerDashboard(){
		ProducerCompany principal = producerService.findByPrincipal();
		
		Long nOfCustomers = 
			customerService.countByContentViewedOfCompany(principal);
		Collection<Content> mostCommentedContent = 
			contentService.FindMostCommentedByProducerCompany(principal);
		Collection<Content> betterAvgContent =
			contentService.findByBetterAvgScoreAndProducerPrincipal();
		
		// The following asserts refer to the actual information
		// displayed on the various tables when the BD is filled
		// with the data of populatedatabase.xml
		
		Assert.isTrue(nOfCustomers == 2);
		Assert.isTrue(mostCommentedContent.size() == 2);
		Assert.isTrue(betterAvgContent.size() == 1);
	}
	
	public void adminDashboard(){
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
		
		// The following asserts refer to the actual information
		// displayed on the various tables when the BD is filled
		// with the data of populatedatabase.xml
		
		Assert.isTrue(producersBetterScore.size() == 1);
		Assert.isTrue(producersWorstScore.size() == 1);
		Assert.isTrue(producersMoreMedia.size() == 1); 
		Assert.isTrue(customersMoreComments.size() == 1);
		Assert.isTrue(customersMoreEpisodes.size() == 1);
		Assert.isTrue(customersMoreFilms.size() == 1);
	}
}
