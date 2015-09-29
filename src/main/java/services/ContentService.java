package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ContentRepository;
import domain.Content;
import domain.ProducerCompany;

@Transactional
@Service
public class ContentService {
	
	// Managed repository -------------------------------------------------
	@Autowired
	private ContentRepository contentRepository;
	
	// Supporting services ------------------------------------------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private ProducerCompanyService proudcerService;
	
	// Simple CRUD methods ------------------------------------------------
	/** Custom findOne using a query due to problems with
	 * retrieving a film or tv serie as Content */
	public Content findOne(int contentId){
		Content result;
		result = contentRepository.customFindOne(contentId);
		return result;
	}
	
	// Other business methods ---------------------------------------------
	public Collection<Object[]> findOrderedByAverageScoreAndProducer(){
		ProducerCompany principal = proudcerService.findByPrincipal();
		Collection<Object[]> result = 
			contentRepository.finbByProducerOrderedByAverageScore(principal);
		return result;
	}
	
	/** Method used to fix a bug retrieving mark.content from a mark
	 * where content is not an episode */
	public Content findByMark(int markId){
		return contentRepository.findByMark(markId);
	}
	
	// Producer dahsboard methods -----------------------------------------
	public Collection<Content> findByBetterAvgScoreAndProducerPrincipal(){
		actorService.checkAuhtority("PRODUCER");
		Collection<Object[]> contentPlusAverageRating;
		Object[] first = null;
		
		contentPlusAverageRating = findOrderedByAverageScoreAndProducer();
		if(!contentPlusAverageRating.isEmpty()){
			first = (Object[]) contentPlusAverageRating.toArray()[0];
		}else{
			return null;
		}
		Double betterAverageScore = (Double) first[1];
		List<Content> result = new ArrayList<Content>();
		for(Object[] elem : contentPlusAverageRating){
			if(elem[1] == betterAverageScore){
				Content content = (Content) elem[0];
				result.add(content);
			}
		}
		return result;
	}
	
	public Collection<Content> FindMostCommentedByProducerCompany(ProducerCompany producer){
		actorService.checkAuhtority("PRODUCER");
		Collection<Content> result = contentRepository.FindMostCommentedByProducerCompany(producer);
		return result;
	}
}
