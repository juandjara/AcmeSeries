package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Content;
import domain.Customer;
import domain.Mark;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Integer> {

	@Query("select avg(m.score) from Mark m where m.content =?1")
	Double findAverageScoreByContent(Content content);
	
	@Query("select m from Mark m where m.customer = ?1 and m.content = ?2")
	Mark findByCustomerAndContent(Customer customer, Content content);
	
	@Query("select m from Mark m " +
			"where m.isViewed = true and m.customer = ?1")
	Collection<Mark> findViewedByCustomer(Customer customer);
}
