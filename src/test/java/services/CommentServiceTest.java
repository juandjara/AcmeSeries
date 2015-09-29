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
import domain.Comment;
import domain.Content;
import domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={	
                "classpath:spring/datasource.xml",
                "classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class CommentServiceTest extends AbstractTest{

	public final static int NUMBER_OF_JUAN_COMMENTS = 2;
	public final static int NUMBER_OF_REPLIES = 1;
	public final static int NUMBER_OF_RECOMMENDATIONS_TO_DOE = 1;
	public final static int NUMBER_OF_COMMENTS = 3;
	
	// Service under test ----------------------------------------------
	@Autowired
	private CommentService commentService;
	
	// Supporting services ---------------------------------------------
	@Autowired
	private ContentService contentService;
	@Autowired
	private CustomerService customerService;
	
	// Test cases ------------------------------------------------------
	
	/** REQ06. Positive test case.
	 * Every user, authenticated or not, should be able to
	 * List the comments in a content */
	@Test
	public void TestListByContent(){
		int validId = 23;
		Collection<Comment> comments = commentService.findByContent(validId);
		Assert.isTrue(comments.size() == NUMBER_OF_COMMENTS);
	}
	
	/** REQ06. Negative test case.
	 * We will try to list comments using an invalid content id
	 * simulating a wrong id input in the url */
	@Test(expected=IllegalArgumentException.class)
	public void TestListByContentNegative(){
		int invalidId = 233;
		Collection<Comment> comments = commentService.findByContent(invalidId);
		Assert.isTrue(comments.size() == NUMBER_OF_COMMENTS);
	}
	
	/** REQ07. Positive test case. 
	 * Every user, authenticated or not, should be able to 
	 * list the replies to a comment*/
	@Test
	public void TestListByParent(){
		int validId = 39;
		Collection<Comment> replies = commentService.findByParent(validId);
		Assert.isTrue(replies.size() == NUMBER_OF_REPLIES);
	}
	
	/** REQ07. Negative test case.
	 * We will try to list replies using an invalid parent id
	 * simulating a wrong id input in the url */
	@Test(expected=IllegalArgumentException.class)
	public void TestListByParentNegative(){
		int invalidId = 399;
		Collection<Comment> replies = commentService.findByParent(invalidId);
		Assert.isTrue(replies.size() == NUMBER_OF_REPLIES);
	}
	
	/** REQ15. Positive test case
	 * A user who is authenticated as a customer must be able to
	 * publish a new comment for a content*/
	@Test
	public void testCreateSimple(){
		authenticate("customer");
		createSimple();
	}
	
	/** REQ15. Negative test case 
	 * We will try to post a comment using a wrong authentication
	 * and we will catch the exception */
	@Test(expected=IllegalArgumentException.class)
	public void testCreateSimpleNegative(){
		authenticate("admin");
		createSimple();
	}
	
	/** REQ16. Positive test case.
	 * A user who is authenticated as a customer must be able to
	 * List the comments he or she has published */
	@Test
	public void testListBySenderPrincipal(){
		authenticate("customer");
		Collection<Comment> comments;
		comments = commentService.findBySenderPrincipal();
		Assert.isTrue(comments.size() == NUMBER_OF_JUAN_COMMENTS);
	}
	
	/** REQ16. Negative test case
	 * We will try to list the comments published by the principal
	 * using a wrong authentication
	 * and we will catch the exception */
	@Test(expected=IllegalArgumentException.class)
	public void testListBySenderPrincipalNegative(){
		authenticate("admin");
		Collection<Comment> comments;
		comments = commentService.findBySenderPrincipal();
		Assert.isTrue(comments.size() == NUMBER_OF_JUAN_COMMENTS);
	}
	
	/** REQ17. Positive test case 
	 * A user who is authenticated as a customer must be able to 
	 * reply a comment with a comment */
	@Test
	public void testCreateReply(){
		authenticate("customer");
		createReply();
	}
	
	/** REQ17. Negative test case 
	 * We will try to post a reply using an invalid authentication
	 * and we will catch the exception */
	@Test(expected=IllegalArgumentException.class)
	public void testCreateReplyNegative(){
		authenticate("admin");
		createReply();
	}
	
	/** REQ18. Positive test case 
	 * A user who is authenticated as a customer must be able to
	 * delete one of his/her comments as long as it has no replies */
	@Test
	public void testDelete(){
		authenticate("customer");
		deleteComment();
	}
	
	/** REQ18. Negative test case 
	 * We will try to delete a comment using an invalid authentication
	 * and we will catch the exception */
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteNegative(){
		authenticate("admin");
		deleteComment();
	}
	
	/** REQ19. Positive test case 
	 * A user who is authenticated as a customer must be able to 
	 * send a recommendation of a content to another customer,
	 * That means sharing one of his/her comments. */
	@Test
	public void testRecommend(){
		authenticate("customer");
		createRecommendation();
	}
	
	/** REQ19. Negative test case 
	 * We will try to send a recommendation using an invalid authentication
	 * and we will catch the exception */
	@Test(expected=IllegalArgumentException.class)
	public void testRecommendNegative(){
		authenticate("admin");
		deleteComment();
	}
	
	/** REQ20. Positive test case.
	 * A user who is authenticated as a customer must be able to
	 * List the recommendations that other customers have sent to him/her */
	@Test
	public void testListByReceiverPrincipal(){
		authenticate("johndoe");
		Collection<Comment> recommendations;
		recommendations = commentService.findByReceiverPrincipal();
		Assert.isTrue(recommendations.size() == NUMBER_OF_RECOMMENDATIONS_TO_DOE);
	}
	
	/** REQ20. Negative test case
	 * We will try to list the recommendations of the principal
	 * using a wrong authentication
	 * and we will catch the exception */
	@Test(expected=IllegalArgumentException.class)
	public void testListByReceiverPrincipalNegative(){
		authenticate("admin");
		Collection<Comment> recommendations;
		recommendations = commentService.findByReceiverPrincipal();
		Assert.isTrue(recommendations.size() == 
				NUMBER_OF_RECOMMENDATIONS_TO_DOE);
	}
	
	// Ancillary methods -----------------------------------------------
	public Comment simpleComment(){
		Comment result;
		Content content;
		Date date;
		
		long now = System.currentTimeMillis();
		int secureTimeMargin = 10;
		date = new Date(now - secureTimeMargin);
		content = contentService.findOne(23);
		result = commentService.create(content);
		result.setCreationDate(date);
		result.setText("test comment");
		
		return result;
	}
	
	public void createSimple(){
		// Create and save a new comment
		Comment comment = simpleComment();
		int contentId = comment.getContent().getId();
		Collection<Comment> collection = commentService.findByContent(contentId);
		int oldSize = collection.size();
		commentService.save(comment);
		
		// Check the number of comments has increased
		Collection<Comment> newCollection = commentService.findByContent(contentId);
		int newSize = newCollection.size();
		Assert.isTrue(newSize == oldSize + 1);
	}
	
	public void createReply(){
		// Create and save a new reply
		Comment comment = simpleComment();
		int parentId = 41;
		Collection<Comment> collection = commentService.findByParent(parentId);
		int oldSize = collection.size();
		
		Comment parent = commentService.findOne(parentId);
		Assert.notNull(parent, "null parent");
		comment.setParent(parent);
		parent.getChildren().add(comment);
		
		commentService.save(comment);
		
		// Check the number of replies has increased
		Collection<Comment> newCollection = commentService.findByParent(parentId);
		int newSize = newCollection.size();
		Assert.isTrue(newSize == oldSize + 1);
	}
	
	public void deleteComment(){
		// Delete a comment
		int oldSize = commentService.findBySenderPrincipal().size();
		Comment comment = commentService.findOne(41);
		commentService.delete(comment);
		Customer principal = customerService.findByPrincipal();
		principal.getComments().remove(comment);
		customerService.save(principal);
		
		// Check the number of comments by customer has decreased
		int newSize = commentService.findBySenderPrincipal().size();
		Assert.isTrue(newSize == oldSize - 1);
	}
	
	public void createRecommendation(){
		// Create and save a new reply
		Comment comment = simpleComment();
		int receiverId = 9;
		Collection<Comment> collection = commentService.findByReceiver(receiverId);
		int oldSize = collection.size();
		Customer receiver = customerService.findOne(receiverId);
		comment.setReceiver(receiver);
		receiver.getRecomendations().add(comment);
		
		commentService.save(comment);
		customerService.save(receiver);
		
		// Check the number of recommendations has increased
		Collection<Comment> newCollection = commentService.findByReceiver(receiverId);
		int newSize = newCollection.size();
		Assert.isTrue(newSize == oldSize + 1);
	}
}
