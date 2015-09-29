package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MultimediaRepository;
import domain.Multimedia;
import domain.ProducerCompany;

@Transactional
@Service
public class MultimediaService {

	// Managed repository ----------------------------------------------
	@Autowired
	private MultimediaRepository multimediaRepository;
	
	// Supporting services ---------------------------------------------
	@Autowired
	private ProducerCompanyService producerService;
	@Autowired
	private ActorService actorService;
	
	// Simple CRUD methods ---------------------------------------------
	public Multimedia findOne(int multimediaId){
		Multimedia result;
		result = multimediaRepository.findOne(multimediaId);
		return result;
	}
	
	public void save(Multimedia media){
		Assert.notNull(media);
		checkPrincipal(media);
		multimediaRepository.save(media);
	}
	
	public void flush(){ multimediaRepository.flush(); }
	
	// Other business methods -----------------------------------------
	public Collection<Object[]> findOrderedByAverageScore(){
		Collection<Object[]> result = multimediaRepository.findOrderedByAverageScore();
		return result;
	}
	
	/** Search multimedia by title, genres, tags, or full names of the staff */
	public Collection<Multimedia> findByKeyword(String keyword){
		actorService.checkIsAuthenticated();
		Collection<Multimedia> result;
		Collection<Multimedia> result2;
		
		result = multimediaRepository.findByKeyword(keyword);
		result2 = multimediaRepository.findByStaffKeyword(keyword);
		
		result2.removeAll(result);
		result.addAll(result2);
		return result;
	}
	
	/** Adds a tag to a multimedia.
	 * The tag can contain digits, letters, spaces, underscores and hyphens
	 * but it cannot start with spaces or contain characters like "á é í ó ú"*/
	public void addTag(int multimediaId, String tag){
		boolean tagIsValid = tag.matches("^(?! )[ñÑ\\w\\d- ]+$");
		Assert.isTrue(tagIsValid);
		Multimedia media = findOne(multimediaId);
		Assert.notNull(media, "acme.invalid.identifier");
		checkPrincipal(media);
		Collection<String> tags = media.getTags();
		tags.add(tag);
		save(media);
	}
	
	/** Removes a tag from a multimedia */
	public void removeTag(int multimediaId, String tag){
		Multimedia media = findOne(multimediaId);
		Assert.notNull(media, "acme.invalid.identifier");
		checkPrincipal(media);
		Collection<String> tags = media.getTags();
		tags.remove(tag);
		save(media);
	}
	
	public void checkPrincipal(Multimedia media){
		ProducerCompany principal = producerService.findByPrincipal();
		ProducerCompany producer = media.getProducerCompany();
		Assert.isTrue(principal.equals(producer), "acme.invalid.principal");
	}
}
