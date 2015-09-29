package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.ProducerCompany;
import domain.TVSerie;

@Repository
public interface TVSerieRepository extends JpaRepository<TVSerie, Integer> {
	
	@Query("select t from TVSerie t where t.marks.size = "+
	       "(select max(t2.marks.size) from TVSerie t2 join t2.marks m "+
		   "where m.isViewed = true)")
	Collection<TVSerie> FindMostViewed();
	
	@Query("select t from TVSerie t where t.comments.size = "+
		   "(select max(t2.comments.size) from TVSerie t2)")
	Collection<TVSerie> FindMostCommented();
	
	@Query("select count(t) from TVSerie t join t.marks m "+
	       "where m.isViewed = true and m.customer = ?1")
	Long CountViewedByCustomer(Customer customer);
	
	/** Returns a collection of object arrays
	 * containing, in the first position, the tv serie
	 * and, in the second position, its average rating*/
	@Query("select t, avg(mk.score) from TVSerie t join t.marks mk " +
			"group by t " +
			"order by avg(mk.score) desc")
	Collection<Object[]> findOrderedByAverageScore();
	
	@Query("select t from TVSerie t where t.producerCompany = ?1")
	Collection<TVSerie> findByProducer(ProducerCompany producer);
}
