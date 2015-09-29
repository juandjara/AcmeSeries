package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.Customer;
import domain.ProducerCompany;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	@Query("select c from Customer c where c.userAccount = ?1")
	Customer findByUserAccount(UserAccount userAccount);
	
	@Query("select count(distinct c) from Customer c join c.marks m " +
		   "where m.isViewed = true and m.content.producerCompany = ?1")
	Long countByContentViewedOfCompany(ProducerCompany company);
			
	@Query("select c from Customer c where c.comments.size = "+
		   "(select max(c2.comments.size) from Customer c2)")
	Collection<Customer> findByMoreCommentsCreated();
	
	@Query("select distinct m.customer from Episode e join e.marks m " +
			"where m.isViewed = true " +
			"and m.customer.marks.size =" +
			"(select max(m2.customer.marks.size) from Episode e2 join e2.marks m2" +
			" where m2.isViewed = true) ")
	Collection<Customer> findByMoreEpisodesViewed();
	
	@Query("select distinct m.customer from TVSerie t join t.marks m " +
			"where m.isViewed = true " +
			"and m.customer.marks.size =" +
			"(select max(m2.customer.marks.size) from TVSerie t2 join t2.marks m2" +
			" where m2.isViewed = true) ")
	Collection<Customer> findByMoreTVSeriesViewed();
	
	@Query("select distinct m.customer from Film f join f.marks m " +
			"where m.isViewed = true " +
			"and m.customer.marks.size =" +
			"(select max(m2.customer.marks.size) from Film f2 join f2.marks m2" +
			" where m2.isViewed = true) ")
	Collection<Customer> findByMoreFilmsViewed();
	
}
