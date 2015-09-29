package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MarkRepository;
import domain.Content;
import domain.Customer;
import domain.Mark;

@Transactional
@Service
public class MarkService {

	// Managed repository -------------------------------------------------
	@Autowired
	private MarkRepository markRepository;
	
	// Supporting services ------------------------------------------------
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ContentService contentService;
	
	// Simple CRUD methods ------------------------------------------------
	public Mark create(Content content){
		Assert.notNull(content);
		Mark result = new Mark();
		Customer principal = customerService.findByPrincipal();
		
		result.setContent(content);
		result.setCustomer(principal);
		
		// Initial values for a mark
		result.setIsViewed(false);
		
		return result;
	}
	
	public Mark save(Mark mark){
		Mark result;
		
		Assert.notNull(mark);
		checkPrincipal(mark);
		Customer principal = customerService.findByPrincipal();
		Assert.isTrue(!principal.getIsBanned(), "acme.error.ban.nomark");
		result = markRepository.save(mark);
		
		return result;
	}
	
	public Mark findOne(int markId){
		return markRepository.findOne(markId);
	}
	
	// Other business methods ---------------------------------------------
	public Mark createOrFind(int contentId){
		Mark result;
		Content content;
		Mark prevMark;
		Customer principal;
		
		principal = customerService.findByPrincipal();
		content = contentService.findOne(contentId);
		Assert.notNull(content, "acme.invalid.identifier");
		prevMark = markRepository.findByCustomerAndContent(principal, content);
		
		if(prevMark == null){
			result = create(content);
		}else{
			result = prevMark;
		}
		
		return result;
	}
	
	public Double findAverageScoreByContent(Content content){
		Double result = markRepository.findAverageScoreByContent(content);
		return result;
	}
	
	public Collection<Mark> findByContent(int contentId){
		Content content = contentService.findOne(contentId);
		Collection<Mark> result = content.getMarks();
		return result;
	}
	
	public Collection<Mark> findViewedByCustomerPrincipal(){
		Customer principal = customerService.findByPrincipal();
		Collection<Mark> result;
		result = markRepository.findViewedByCustomer(principal);
		return result;
	}
	
	public void checkPrincipal(Mark mark){
		Customer principal = customerService.findByPrincipal();
		Customer customer = mark.getCustomer();
		Assert.isTrue(principal.equals(customer));
	}
}
