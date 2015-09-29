package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FilmRepository;
import domain.Comment;
import domain.Customer;
import domain.Film;
import domain.Mark;
import domain.ProducerCompany;
import domain.Role;

@Transactional
@Service
public class FilmService {

	// Managed repository ----------------------------------------------
	@Autowired
	private FilmRepository filmRepository;
	
	// Supporting services ---------------------------------------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private ProducerCompanyService producerService;
	@Autowired
	private MultimediaService multimediaService;
	
	// Simple CRUD methods ---------------------------------------------
	public Film findOne(int filmId){
		Film result;
		result = filmRepository.findOne(filmId);
		return result;
	}
	
	public Collection<Film> findAll(){
		Collection<Film> result;
		result = filmRepository.findAll();
		return result;
	}
	
	public Film create(){
		ProducerCompany producer;
		Film result;
		Collection<Comment> comments;
		Collection<Mark> marks;
		Collection<Role> roles;
		Collection<String> tags;
		
		producer = producerService.findByPrincipal();
		result = new Film();
		comments = new ArrayList<Comment>();
		marks = new ArrayList<Mark>();
		roles = new ArrayList<Role>();
		tags = new ArrayList<String>();
		
		result.setMarks(marks);
		result.setComments(comments);
		result.setProducerCompany(producer);
		result.setRoles(roles);
		result.setTags(tags);
		
		return result;
	}
	
	/** Returns the instrumented entity */
	public Film save(Film film){
		Assert.notNull(film);
		multimediaService.checkPrincipal(film);
		return filmRepository.save(film);
	}
	
	public void delete(Film film) {
		Assert.notNull(film);
		multimediaService.checkPrincipal(film);
		filmRepository.delete(film);
	}
	
	public void flush(){filmRepository.flush();}
	
	// Other business methods ------------------------------------------
	public Collection<Object[]> findOrderedByAverageScore(){
		Collection<Object[]> result = filmRepository.findOrderedByAverageScore();
		return result;
	}
	
	public Collection<Film> findByProducerPrincipal(){
		Collection<Film> result;
		ProducerCompany principal;
		
		principal = producerService.findByPrincipal();
		result = filmRepository.findByProducer(principal);
		
		return result;
	}
	
	// General dashboard methods -----------------------------------------------
	public Collection<Film> findMostViewed(){
		actorService.checkIsAuthenticated();
		Collection<Film> result = filmRepository.FindMostViewed();
		return result;
	}
	
	public Collection<Film> findMostCommented(){
		actorService.checkIsAuthenticated();
		Collection<Film> result = filmRepository.FindMostCommented();
		return result;
	}
	
	public Collection<Film> findByBetterAverageScore(){
		actorService.checkIsAuthenticated();
		Collection<Object[]> contentPlusAverageRating = findOrderedByAverageScore();
		Object[] first = (Object[]) contentPlusAverageRating.toArray()[0];
		Double betterAverageScore = (Double) first[1];
		List<Film> result = new ArrayList<Film>();
		for(Object[] elem : contentPlusAverageRating){
			if(elem[1] == betterAverageScore){
				Film content = (Film) elem[0];
				result.add(content);
			}
		}
		return result;
	}
	
	// Customer dahsboard methods
	public Long CountViewedByCustomer(Customer customer){
		Assert.notNull(customer);
		actorService.checkAuhtority("CUSTOMER");
		Long result = filmRepository.CountViewedByCustomer(customer);
		return result;
	}
}
