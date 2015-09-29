package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EpisodeRepository;
import domain.Comment;
import domain.Customer;
import domain.Episode;
import domain.Mark;
import domain.TVSerie;

@Transactional
@Service
public class EpisodeService {

	// Managed repository ----------------------------------------------
	@Autowired
	private EpisodeRepository episodeRepository;
	
	// Supporting services ---------------------------------------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private MultimediaService multimediaService;
	@Autowired
	private TVSerieService serieService;
	
	// Simple CRUD methods ---------------------------------------------
	public Episode findOne(int episodeId){
		Episode result;
		result = episodeRepository.findOne(episodeId);
		return result;
	}
	
	public Episode create(TVSerie serie){
		Episode result;
		Collection<Comment> comments;
		Collection<Mark> marks;
		Collection<Episode> prevEps;
		
		result = new Episode();
		comments = new ArrayList<Comment>();
		marks = new ArrayList<Mark>();
		prevEps = serie.getEpisodes();
		
		result.setSerie(serie);
		result.setComments(comments);
		result.setMarks(marks);
		result.setEpisodeNumber(prevEps.size()+ 1);
		
		return result;
	}
	
	/** Returns the instrumented entity */
	public Episode save(Episode episode){
		Episode result;
		
		Assert.notNull(episode);
		TVSerie serie = episode.getSerie();
		multimediaService.checkPrincipal(serie);
		result = episodeRepository.saveAndFlush(episode);
		serie.getEpisodes().add(result);
		serie = serieService.save(serie);
		
		return result;
	}
	
	public void delete(Episode episode){
		Assert.notNull(episode);
		TVSerie serie = episode.getSerie();
		multimediaService.checkPrincipal(serie);
		episodeRepository.delete(episode);
		serie.getEpisodes().remove(episode);
		serieService.save(serie);
	}
	
	public void flush(){episodeRepository.flush();}
	
	// Other business methods -----------------------------------------
	public Collection<Object[]> findOrderedByAverageScore(){
		Collection<Object[]> result = episodeRepository.findOrderedByAverageScore();
		return result;
	}
	
	public Collection<Episode> findBySeasonAndTVSerie(TVSerie serie, Integer season){
		Collection<Episode> result;
		result = episodeRepository.findBySeasonAndTVSerie(serie, season);
		return result;
	}
	
	public Collection<Episode> findByTVSerie(int serieId){
		TVSerie serie = serieService.findOne(serieId);
		Collection<Episode> result = serie.getEpisodes();
		return result;
	}
	
	// General dashboard methods -----------------------------------------------
	public Collection<Episode> findMostViewed(){
		actorService.checkIsAuthenticated();
		Collection<Episode> result = episodeRepository.FindMostViewed();
		return result;
	}
	
	public Collection<Episode> findMostCommented(){
		actorService.checkIsAuthenticated();
		Collection<Episode> result = episodeRepository.FindMostCommented();
		return result;
	}
	public Collection<Episode> findByBetterAverageScore(){
		actorService.checkIsAuthenticated();
		Collection<Object[]> contentPlusAverageRating = findOrderedByAverageScore();
		Object[] first = (Object[]) contentPlusAverageRating.toArray()[0];
		Double betterAverageScore = (Double) first[1];
		List<Episode> result = new ArrayList<Episode>();
		for(Object[] elem : contentPlusAverageRating){
			if(elem[1] == betterAverageScore){
				Episode content = (Episode) elem[0];
				result.add(content);
			}
		}
		return result;
	}
	
	// Customer dahsboard methods
	public Long CountViewedByCustomer(Customer customer){
		Assert.notNull(customer);
		actorService.checkAuhtority("CUSTOMER");
		Long result = episodeRepository.CountViewedByCustomer(customer);
		return result;
	}
}
