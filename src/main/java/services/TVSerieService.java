package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TVSerieRepository;
import domain.Comment;
import domain.Customer;
import domain.Episode;
import domain.Mark;
import domain.ProducerCompany;
import domain.Role;
import domain.TVSerie;

@Transactional
@Service
public class TVSerieService {

	// Managed repository ----------------------------------------------
	@Autowired
	private TVSerieRepository tvserieRepository;
	
	// Supporting services ---------------------------------------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private ProducerCompanyService producerService;
	@Autowired
	private MultimediaService multimediaService;
	
	// Simple CRUD methods ---------------------------------------------
	public Collection<TVSerie> findAll(){
		Collection<TVSerie> result;
		result = tvserieRepository.findAll();
		return result;
	}
	
	public TVSerie findOne(int tvserieId){
		TVSerie result;
		result = tvserieRepository.findOne(tvserieId);
		return result;
	}
	
	public TVSerie create(){
		ProducerCompany producer;
		TVSerie result;
		Collection<Comment> comments;
		Collection<Mark> marks;
		Collection<Episode> episodes;
		Collection<Role> roles;
		Collection<String> tags;
		
		producer = producerService.findByPrincipal();
		result = new TVSerie();
		comments = new ArrayList<Comment>();
		marks = new ArrayList<Mark>();
		episodes = new ArrayList<Episode>();
		roles = new ArrayList<Role>();
		tags = new ArrayList<String>();
		
		result.setMarks(marks);
		result.setComments(comments);
		result.setProducerCompany(producer);
		result.setEpisodes(episodes);
		result.setRoles(roles);
		result.setTags(tags);
		
		return result;
	}
	
	/** Returns the instrumented entity */
	public TVSerie save(TVSerie serie){
		Assert.notNull(serie);
		multimediaService.checkPrincipal(serie);
		return tvserieRepository.save(serie);
	}
	
	public void delete(TVSerie serie){
		Assert.notNull(serie);
		multimediaService.checkPrincipal(serie);
		tvserieRepository.delete(serie);
	}
	
	public void flush(){ tvserieRepository.flush(); }
	
	// Other business methods ------------------------------------------
	public Collection<TVSerie> findByProducerPrincipal(){
		Collection<TVSerie> result;
		ProducerCompany principal;
		
		principal = producerService.findByPrincipal();
		result = tvserieRepository.findByProducer(principal);
		
		return result;
	}
	
	public Collection<Object[]> findOrderedByAverageScore(){
		Collection<Object[]> result = tvserieRepository.findOrderedByAverageScore();
		return result;
	}
	
	// General dashboard methods -----------------------------------------------
	public Collection<TVSerie> findMostViewed(){
		actorService.checkIsAuthenticated();
		Collection<TVSerie> result = tvserieRepository.FindMostViewed();
		return result;
	}
	
	public Collection<TVSerie> findMostCommented(){
		actorService.checkIsAuthenticated();
		Collection<TVSerie> result = tvserieRepository.FindMostCommented();
		return result;
	}
	public Collection<TVSerie> findByBetterAverageScore(){
		actorService.checkIsAuthenticated();
		Collection<Object[]> contentPlusAverageRating = findOrderedByAverageScore();
		Object[] first = (Object[]) contentPlusAverageRating.toArray()[0];
		Double betterAverageScore = (Double) first[1];
		List<TVSerie> result = new ArrayList<TVSerie>();
		for(Object[] elem : contentPlusAverageRating){
			if(elem[1] == betterAverageScore){
				TVSerie content = (TVSerie) elem[0];
				result.add(content);
			}
		}
		return result;
	}
	
	// Customer dahsboard methods
	public Long CountViewedByCustomer(Customer customer){
		Assert.notNull(customer);
		actorService.checkAuhtority("CUSTOMER");
		Long result = tvserieRepository.CountViewedByCustomer(customer);
		return result;
	}
}
