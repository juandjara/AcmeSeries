package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.Film;
import domain.ProducerCompany;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {
	
	@Query("select f from Film f where f.marks.size = "+
	       "(select max(f2.marks.size) from Film f2 join f2.marks m "+
		   "where m.isViewed = true)")
	Collection<Film> FindMostViewed();
	
	@Query("select f from Film f where f.comments.size = "+
		   "(select max(f2.comments.size) from Film f2)")
	Collection<Film> FindMostCommented();
	
	@Query("select count(f) from Film f join f.marks m "+
	       "where m.isViewed = true and m.customer = ?1")
	Long CountViewedByCustomer(Customer customer);
	
	/** Returns a collection of object arrays
	 * containing, in the first position, the film
	 * and, in the second position, its average rating*/
	@Query("select f, avg(mk.score) from Film f join f.marks mk " +
			"group by f " +
			"order by avg(mk.score) desc")
	Collection<Object[]> findOrderedByAverageScore();
	
	@Query("select f from Film f where f.producerCompany = ?1")
	Collection<Film> findByProducer(ProducerCompany producer);
}
