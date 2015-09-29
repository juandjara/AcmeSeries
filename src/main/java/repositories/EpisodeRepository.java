package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.Episode;
import domain.TVSerie;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Integer> {
	
	@Query("select e from Episode e where e.marks.size = "+
	       "(select max(e2.marks.size) from Episode e2 join e2.marks m "+
		   "where m.isViewed = true)")
	Collection<Episode> FindMostViewed();
	
	@Query("select e from Episode e where e.comments.size = "+
		   "(select max(e2.comments.size) from Episode e2)")
	Collection<Episode> FindMostCommented();
	
	@Query("select count(e) from Episode e join e.marks m "+
	       "where m.isViewed = true and m.customer = ?1")
	Long CountViewedByCustomer(Customer customer);
	
	/** Returns a collection of object arrays
	 * containing, in the first position, the episode
	 * and, in the second position, its average rating*/
	@Query("select e, avg(mk.score) from Episode e join e.marks mk " +
			"group by e " +
			"order by avg(mk.score) desc")
	Collection<Object[]> findOrderedByAverageScore();
	
	@Query("select e from Episode e where e.serie = ?1 and e.seasonNumber = ?2")
	Collection<Episode> findBySeasonAndTVSerie(TVSerie serie, Integer season);
}
