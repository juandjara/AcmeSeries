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
import domain.Content;
import domain.Mark;

@RunWith(SpringJUnit4ClassRunner.class)	
@ContextConfiguration(locations={		
                "classpath:spring/datasource.xml",
                "classpath:spring/config/packages.xml"})	
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class MarkServiceTest extends AbstractTest{

	public static final int NUMBER_OF_MARKS = 2;
	public static final int VIEWED_CONTENTS = 3;
	
	// Service under test -----------------------------------------------
	@Autowired
	private MarkService markService;
	
	// Supporting services ----------------------------------------------
	@Autowired
	private ContentService contentService;
	
	// Test cases -------------------------------------------------------
	
	/** REQ08. Positive test case 
	 * Every user, authenticated or not, must be able to
	 * list the scores users have given a content
	 * and the average score */
	@Test
	public void testListScore(){
		int validId = 24;
		Collection<Mark> marks;
		marks = markService.findByContent(validId);
		Assert.isTrue(marks.size() == NUMBER_OF_MARKS);
	}
	
	/** REQ08. Negative test case 
	 * We will try to list the scores using an invalid content id
	 * simulating an invalid id input on the url */
	@Test(expected=NullPointerException.class)
	public void testListScoreNegative(){
		int invalidId = 244;
		Collection<Mark> marks;
		marks = markService.findByContent(invalidId);
		Assert.isTrue(marks.size() == NUMBER_OF_MARKS);
	}
	
	/** REQ12. Positive test case.
	 * A user who is authenticated as a customer must be able to
	 * Mark/unmark a content as viewed as long as it's not marked/unmarked. */
	@Test
	public void testMark(){
		authenticate("customer");
		markContent();
	}
	
	/** REQ12. Negative test case.
	 * We will try to mark a content using an invalid authentication
	 * and we will catch the exception */
	@Test(expected=IllegalArgumentException.class)
	public void testMarkNegative(){
		authenticate("producer");
		markContent();
	}
	
	/** REQ13. Positive test case.
	 * A user who is authenticated as a customer must be able to
	 * List his/her marked content. */
	@Test
	public void testListViewed(){
		authenticate("customer");
		Collection<Mark> marks;
		marks = markService.findViewedByCustomerPrincipal();
		Assert.isTrue(marks.size() == VIEWED_CONTENTS);
	}
	
	/** REQ13. Negative test case.
	 * We will try to list the marked content using an invalid authentication
	 * and we will catch the exception */
	@Test(expected=IllegalArgumentException.class)
	public void testListViewedNegative(){
		authenticate("producer");
		Collection<Mark> marks;
		marks = markService.findViewedByCustomerPrincipal();
		Assert.isTrue(marks.size() == VIEWED_CONTENTS);
	}
	
	/** REQ14. Positive test case.
	 * A user who is authenticated as a customer must be able to
	 * Give a score to a content and or edit the score given to a content, */
	@Test
	public void testGiveScore(){
		authenticate("customer");
		giveScore();
	}
	
	/** REQ14. Negative test case.
	 * We will try to give a score using an invalid authentication
	 * and we will catch the exception  */
	@Test(expected=IllegalArgumentException.class)
	public void testGiveScoreNegative(){
		authenticate("producer");
		giveScore();
	}
	
	// Ancillary methods ------------------------------------------------
	
	public Mark templateMark(){
		int contentId = 26;
		Mark result = markService.createOrFind(contentId);
		return result;
	}
	
	public void markContent(){
		// Create a new mark for a content 
		Collection<Mark> collection = markService.findViewedByCustomerPrincipal();
		Assert.notNull(collection);
		int oldSize = collection.size();
		Mark mark = templateMark();
		mark.setIsViewed(true);
		markService.save(mark);
		
		// check the number of viewed contents of the principal has increased
		
		Collection<Mark> newCollection;
		newCollection = markService.findViewedByCustomerPrincipal();
		Assert.notNull(newCollection);
		int newSize = newCollection.size();
		Assert.isTrue(newSize == oldSize + 1);
	}
	
	public void giveScore(){
		// Give a score to a content
		int contentId = 26;
		Collection<Mark> collection = markService.findByContent(contentId);
		int oldSize = collection.size();
		Mark mark = templateMark();
		mark.setScore(4);
		mark = markService.save(mark);
		Content content = contentService.findOne(contentId);
		// I don't know why, but this magically works, no save required
		content.getMarks().add(mark); 
		
		// check the number of marks for that content has increased
		Collection<Mark> newCollection = markService.findByContent(contentId);
		int newSize = newCollection.size();
		Assert.isTrue(newSize == oldSize + 1);
	}
}
