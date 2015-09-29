package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.ProducerCompany;

@Repository
public interface ProducerCompanyRepository extends JpaRepository<ProducerCompany, Integer> {
	
	@Query("select p from ProducerCompany p where p.userAccount = ?1")
	ProducerCompany findByUserAccount(UserAccount userAccount);
	
	@Query("select p from ProducerCompany p where p.multimedias.size = "+
		   "(select max(p2.multimedias.size) from ProducerCompany p2)")
	Collection<ProducerCompany> findByMoreMultimediaCreated();
}
