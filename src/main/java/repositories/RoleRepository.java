package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Multimedia;
import domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	@Query("select r from Role r where r.multimedia = ?1")
	Collection<Role> findByMultimedia(Multimedia media);
	
}
