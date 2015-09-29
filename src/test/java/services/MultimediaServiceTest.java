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
import domain.Multimedia;

@RunWith(SpringJUnit4ClassRunner.class)	
@ContextConfiguration(locations={		
                "classpath:spring/datasource.xml",
                "classpath:spring/config/packages.xml"})	
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class MultimediaServiceTest extends AbstractTest{
	
	public static final int SEARCH_RESULTS = 2;
	
	// Service under test -------------------------------------------------
	@Autowired
	private MultimediaService mediaService;
	
	// Supporting services ------------------------------------------------
	
	// Test cases ---------------------------------------------------------
	
	/** REQ10. Positive test case.
	 * Every authenticated user must be able to
	 * search for tv series and films using a keyword contained in 
	 * the multimedia's title, genres, tags, writers, directors
	 * or starring actors. The result should be able to be ordered by
	 * title, release date or average score. */
	@Test
	public void testSearch(){
		authenticate("customer");
		Collection<Multimedia> medias;
		medias = mediaService.findByKeyword("Test");
		Assert.isTrue(medias.size() == SEARCH_RESULTS);
	}
	
	/** REQ10. Negative test case.
	 * We will try to search without authenticating to the system and
	 * we will catch the exception */
	@Test(expected=IllegalArgumentException.class)
	public void testSearchNegative(){
		Collection<Multimedia> medias;
		medias = mediaService.findByKeyword("Test");
		Assert.isTrue(medias.size() == SEARCH_RESULTS);
	}
	
}
