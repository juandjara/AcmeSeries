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
import domain.Episode;
import domain.TVSerie;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={	
                "classpath:spring/datasource.xml",
                "classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class EpisodeServiceTest extends AbstractTest{

	public static final int NUMBER_OF_EPISODES = 3;
	
	// Service under test ----------------------------------------------
	@Autowired
	private EpisodeService episodeService;
	
	// Supporting services ---------------------------------------------
	@Autowired
	private TVSerieService tvSerieService;
	
	// Test cases ------------------------------------------------------
	/** REQ05. Positive test case.
	 * Any user, authenticated or not, must be able to
	 * list the episodes for a tv serie and the details for an episode */
	@Test
	public void testList(){
		// list the episodes in a tv serie
		TVSerie serie = tvSerieService.findOne(24);
		Assert.notNull(serie);
		Collection<Episode> episodes = serie.getEpisodes();
		Assert.isTrue(episodes.size() == NUMBER_OF_EPISODES);
		// and the details for a episode
		Episode episode = episodeService.findOne(25);
		Assert.notNull(episode);
	}
	/** REQ05. Negative test case.
	 * We will try to list the details of an episode using a wrong id
	 * simulating a wrong id input in the url */
	@Test(expected=IllegalArgumentException.class)
	public void testListNegative(){
		// list the episodes in a tv serie
		TVSerie serie = tvSerieService.findOne(24);
		Assert.notNull(serie);
		Collection<Episode> episodes = serie.getEpisodes();
		Assert.isTrue(episodes.size() == NUMBER_OF_EPISODES);
		// and the details for a episode
		Episode episode = episodeService.findOne(21);
		Assert.notNull(episode);
	}
	
	/** REQ26. Positive test case.
	 * A user authenticated as a producer company must be able to
	 * Manage the episodes in one of his/her tv series
	 * Managing implies creating, modifying and deleting */
	@Test
	public void testCRUD(){
		authenticate("producerHBO");
		EpisodeCRUD();
	}
	
	/** REQ26. Negative test case.
	 * We will try to run the CRUD method with a wrong authentication
	 * and we will catch the exception */
	@Test(expected=IllegalArgumentException.class)
	public void testCRUDNegeative(){
		authenticate(null);
		EpisodeCRUD();
	}
	
	// Ancillary methods -----------------------------------------------
	public void EpisodeCRUD(){
		// Creating a new episode
		TVSerie serie = tvSerieService.findOne(24);
		int oldSize = serie.getEpisodes().size();
		Episode episode = templateEpisode();
		episode = episodeService.save(episode);
		
		// checking the number of episodes has increased
		serie = tvSerieService.findOne(24);
		int newSize = serie.getEpisodes().size();
		Assert.isTrue(newSize == oldSize + 1);
		
		// Deleting the previously created episode
		episodeService.delete(episode);
		
		// checking the number of episodes has decreased
		serie = tvSerieService.findOne(24);
		Collection<Episode> finalCollection = serie.getEpisodes();
		long newSize2 = finalCollection.size();
		Assert.isTrue(newSize2 == newSize -1);
	}
	
	public Episode templateEpisode(){
		TVSerie serie = tvSerieService.findOne(24);
		Episode result = episodeService.create(serie);
		
		result.setRunningTime(60);
		result.setTitle("Episode 3");
		result.setSeasonNumber(1);
		
		return result;
	}
}
