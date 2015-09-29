package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ProducerCompany;
import domain.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {

	@Query("select s from Staff s where s.producer = ?1")
	Collection<Staff> findByProducer(ProducerCompany producer);
	
}
