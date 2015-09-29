package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import utils.AbstractTest;
import domain.TVSerie;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={	
                "classpath:spring/datasource.xml",
                "classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class TVSerieServiceTest extends AbstractTest{

	public static final int NUMBER_OF_SERIES = 2;
	public static final int NUMBER_OF_HBO_SERIES = 1;
	
	// Service under test ----------------------------------------------
	@Autowired
	private TVSerieService tvserieService;
	
	// Supporting services ---------------------------------------------
	
	// Test cases ------------------------------------------------------
	
	/** REQ04. Positive test case.
	 * Any user, authenticated or not, must be able to
	 * list the TV series in the system and the details for a TV Serie */
	@Test
	public void testList(){
		// list the tvseries in the system
		Collection<TVSerie> tvseries = tvserieService.findAll();
		Assert.isTrue(tvseries.size() == NUMBER_OF_SERIES);
		// and the details for a tvserie
		TVSerie tvserie = tvserieService.findOne(24);
		Assert.notNull(tvserie);
	}
	/** REQ04. Negative test case.
	 * We will try to list the details using a wrong id
	 * simulating a wrong id input in the url */
	@Test(expected=IllegalArgumentException.class)
	public void testListNegative(){
		// list the tvseries in the system
		Collection<TVSerie> tvseries = tvserieService.findAll();
		Assert.isTrue(tvseries.size() == NUMBER_OF_SERIES);
		// and the details for a tvserie
		TVSerie tvserie = tvserieService.findOne(21);
		Assert.notNull(tvserie);
	}
	
	/** REQ23. Positive test case.
	 * A user authenticated as a producer company must be able to
	 * List the tv series the company has created */
	@Test
	public void testListByProducerPrincipal(){
		authenticate("producerHBO");
		Collection<TVSerie> series;
		series = tvserieService.findByProducerPrincipal();
		Assert.isTrue(series.size() == NUMBER_OF_HBO_SERIES);
	}
	
	/** REQ23. Negative test case
	 * We will try to list the tv series of the principal
	 * with a wrong authentication 
	 * and we will catch the exception. */
	@Test(expected=IllegalArgumentException.class)
	public void testListByProducerPrincipalNegative(){
		authenticate("customer");
		Collection<TVSerie> series;
		series = tvserieService.findByProducerPrincipal();
		Assert.isTrue(series.size() == NUMBER_OF_HBO_SERIES);
	}
	
	/** REQ25. Positive test case.
	 * A user authenticated as a producer company must be able to
	 * Manage his or her content
	 * Managing implies creating, listing, modifying and deleting */
	@Test
	public void testCRUD(){
		authenticate("producer");
		TVSerieCRUD();
	}
	
	/** REQ25. Negative test case.
	 * We will try to run the CRUD method with a wrong authentication
	 * and we will catch the exception */
	@Test(expected=IllegalArgumentException.class)
	public void testCRUDNegeative(){
		authenticate(null);
		TVSerieCRUD();
	}
	
	// Ancillary methods -----------------------------------------------
	public void TVSerieCRUD(){
		// Creating a new serie
		int oldSize = tvserieService.findByProducerPrincipal().size();
		TVSerie tvserie = templateTVSerie();
		tvserie = tvserieService.save(tvserie);
		tvserieService.flush();
		
		// Checking the number of series has increased
		int newSize = tvserieService.findByProducerPrincipal().size();
		Assert.isTrue(newSize == oldSize + 1);
		
		// Deleting the previously created serie
		tvserieService.delete(tvserie);
		tvserieService.flush();
		
		// Checking that the number of series has decreased
		Collection<TVSerie> finalCollection = tvserieService.findByProducerPrincipal();
		int newNewSize = finalCollection.size();
		Assert.isTrue(newNewSize == newSize - 1);
	}
	
	public TVSerie templateTVSerie(){
		TVSerie result = tvserieService.create();
		
		result.setDistributionCompany("team15");
		result.setGenres("Horror");
		result.setOriginalChannel("Your mind");
		result.setOriginCountry("sPAIN");
		result.setReleaseDate(new Date());
		result.setSinopsis("We all are gonna die. Watch it.");
		result.setTitle("The Last Test (Series)");
		
		return result;
	}
}
