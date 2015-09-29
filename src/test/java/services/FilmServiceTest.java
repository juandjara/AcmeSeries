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
import domain.Film;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={	
                "classpath:spring/datasource.xml",
                "classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class FilmServiceTest extends AbstractTest{

	public static final int NUMBER_OF_FILMS = 2;
	public static final int NUMBER_OF_FOX_FILMS = 1;
	
	// Service under test ----------------------------------------------
	@Autowired
	private FilmService filmService;
	
	// Supporting services ---------------------------------------------
	
	// Test cases ------------------------------------------------------
	
	/** REQ03. Positive test case.
	 * Any user, authenticated or not, must be able to
	 * list the films in the system and the details for a film */
	@Test
	public void testList(){
		// list the films in the system
		Collection<Film> films = filmService.findAll();
		Assert.isTrue(films.size() == NUMBER_OF_FILMS);
		// and the details for a film
		Film film = filmService.findOne(22);
		Assert.notNull(film);
	}
	/** REQ03. Negative test case.
	 * We will try to list the details of a film using a wrong id
	 * simulating a wrong id input in the url */
	@Test(expected=IllegalArgumentException.class)
	public void testListNegative(){
		// list the films in the system
		Collection<Film> films = filmService.findAll();
		Assert.isTrue(films.size() == NUMBER_OF_FILMS);
		// and the details for a film
		Film film = filmService.findOne(21);
		Assert.notNull(film);
	}
	
	/** REQ22. Positive test case.
	 * A user authenticated as a producer company must be able to
	 * List the films the company has created */
	@Test
	public void testListByProducerPrincipal(){
		authenticate("producerFOX");
		Collection<Film> films;
		films = filmService.findByProducerPrincipal();
		Assert.isTrue(films.size() == NUMBER_OF_FOX_FILMS);
	}
	
	/** REQ22. Negative test case
	 * We will try to list the films of the principal
	 * with a wrong authentication 
	 * and we will catch the exception. */
	@Test(expected=IllegalArgumentException.class)
	public void testListByProducerPrincipalNegative(){
		authenticate("customer");
		Collection<Film> films;
		films = filmService.findByProducerPrincipal();
		Assert.isTrue(films.size() == NUMBER_OF_FOX_FILMS);
	}
	
	/** REQ24. Positive test case.
	 * A user authenticated as a producer company must be able to
	 * Manage his or her films.
	 * Managing implies creating, modifying and deleting */
	@Test
	public void testCRUD(){
		authenticate("producer");
		FilmCRUD();
	}
	
	/** REQ24. Negative test case.
	 * We will try to run the CRUD method with a wrong authentication
	 * and we will catch the exception */
	@Test(expected=IllegalArgumentException.class)
	public void testCRUDNegeative(){
		authenticate(null);
		FilmCRUD();
	}
	
	// Ancillary methods -----------------------------------------------
	public void FilmCRUD(){
		// Creating a new film
		int oldSize = filmService.findByProducerPrincipal().size();
		Film film = templateFilm();
		film = filmService.save(film);
		filmService.flush();
		
		// Checking the number of films has increased
		int newSize = filmService.findByProducerPrincipal().size();
		Assert.isTrue(newSize == oldSize + 1);
		
		// Deleting the previously created film
		filmService.delete(film);
		filmService.flush();
		
		// Checking the film size has decreased
		Collection<Film> finalCollection = filmService.findByProducerPrincipal();
		int newSize2 = finalCollection.size();
		Assert.isTrue(newSize2 == newSize - 1);
	}
	
	public Film templateFilm(){
		Film result = filmService.create();
		
		result.setBoxOffice(999999999.0);
		result.setBudget(10000000.0);
		result.setDistributionCompany("team15");
		result.setGenres("Horror");
		result.setOriginCountry("sPAIN");
		result.setReleaseDate(new Date());
		result.setRunningTime(200);
		result.setSinopsis("We all are gonna die. Watch it.");
		result.setTitle("The Last Test");
		
		return result;
	}
}
