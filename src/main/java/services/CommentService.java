package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Comment;
import domain.Content;
import domain.Customer;
import forms.CommentForm;

@Transactional
@Service
public class CommentService {

	// Managed repository
	@Autowired
	private CommentRepository commentRepository;
	
	// Supporting services
	@Autowired
	private ActorService actorService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ContentService contentService;
	
	// Simple CRUD methods
	public Comment findOne(int commentId){
		Comment result;
		result = commentRepository.findOne(commentId);
		return result;
	}
	
	public Comment create(Content content){
		Comment result;
		result = createComment(content, null, null);
		return result;
	}
	
	public void save(Comment comment){
		Assert.notNull(comment);
		
		// Sender and receiver cannot be the same
		Customer sender = comment.getSender();
		Customer receiver = comment.getReceiver();
		Assert.isTrue(!sender.equals(receiver));
		
		// Parents and children can't coincide
		Comment parent = comment.getParent();
		Collection<Comment> children = comment.getChildren();
		Assert.isTrue(!children.contains(parent));
		Assert.isTrue(!children.contains(comment));
		
		checkPrincipal(comment);
		commentRepository.saveAndFlush(comment);
	}
	
	/**A comment can only be deleted if it has no replies*/
	public void delete(Comment comment){
		Assert.notNull(comment);
		checkPrincipal(comment);
		Collection<Comment> children = comment.getChildren();
		Assert.isTrue(children.isEmpty());
		commentRepository.delete(comment);
	}
	
	// Other business methods
	private Comment createComment(Content content, Comment parent, Customer receiver){
		Assert.notNull(content);
		Comment result;
		Customer sender;
		Collection<Comment> children;
		
		result = new Comment();
		sender = customerService.findByPrincipal();
		children = new ArrayList<Comment>();
		
		result.setSender(sender);
		result.setReceiver(receiver);
		result.setContent(content);
		result.setParent(parent);
		result.setChildren(children);
		
		return result;
	}
	
	public Comment createReply(Content content, Comment parent){
		Assert.notNull(parent);
		Comment result;
		result = createComment(content, parent, null);
		return result;
	}
	
	public Comment createRecommendation(Content content, Customer receiver){
		Assert.notNull(receiver);
		Comment result;
		result = createComment(content, null, receiver);
		return result;
	}
	
	public Collection<Comment> findByContent(int contentId){
		Content content = contentService.findOne(contentId);
		Assert.notNull(content, "acme.invalid.identifier");
		Collection<Comment> result = 
			commentRepository.findByContentOrderedByDate(content);
		return result;
	}
	
	public Collection<Comment> findByParent(int commentId){
		Comment parent = findOne(commentId);
		Assert.notNull(parent, "acme.invalid.identifier");
		Collection<Comment> result = parent.getChildren();
		result.size(); // prevent lazy init problems
		return result;
	}
	
	public Collection<Comment> findBySenderPrincipal(){
		Customer sender = customerService.findByPrincipal();
		Collection<Comment> result = sender.getComments();
		return result;
	}
	
	public Collection<Comment> findByReceiverPrincipal(){
		Customer receiver = customerService.findByPrincipal();
		Collection<Comment> result = receiver.getRecomendations();
		return result;
	}
	
	public Collection<Comment> findByReceiver(int receiverId){
		Customer receiver = customerService.findOne(receiverId);
		Collection<Comment> result = receiver.getRecomendations();
		return result;
	}
	
	public void checkPrincipal(Comment comment){
		Customer principal = customerService.findByPrincipal();
		Customer sender = comment.getSender();
		Assert.isTrue(principal.equals(sender));
	}
	
	public Comment reconstruct(CommentForm form){
		Comment result;
		Customer principal;
		int contentId = form.getContentId();
		int parentId = form.getParentId();
		int receiverId = form.getReceiverId();
		
		principal = customerService.findByPrincipal();
		Assert.isTrue(!principal.getIsBanned(), "acme.error.ban.nocomment");
		
		Content content = contentService.findOne(contentId);
		Assert.notNull(content, "acme.invalid.identifier");
		
		Comment parent = findOne(parentId);
		Customer receiver = customerService.findOne(receiverId);
		
		if(parent == null && receiver != null){
			result = createRecommendation(content, receiver);
		}else if(receiver == null && parent != null){
			result = createReply(content, parent);
		}else{
			result = create(content);
		}
		result.setText(form.getText());
		int timeMargin = 10;
		Long now = System.currentTimeMillis();
		Date date = new Date(now - timeMargin);
		result.setCreationDate(date);
		
		return result;
	}
	
	// Customer dashboard methods
	public int numberOfcommentsByPrincipalCustomer(){
		actorService.checkAuhtority("CUSTOMER");
		
		Customer principal = customerService.findByPrincipal();
		Collection<Comment> comments = principal.getComments();
		int result = 0;
		if(comments != null)
			result = comments.size();
		return result;
	}
	
	public Collection<Comment> commentsWithMoreRepliesByCustomerPrincipal(){
		Customer principal;
		Collection<Comment> result;
		
		principal = customerService.findByPrincipal();
		result = commentRepository.commentsWithMoreRepliesByCustomer(principal);
		
		return result;
	}
}
