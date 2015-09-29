package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Multimedia;

@Repository
public interface MultimediaRepository extends JpaRepository<Multimedia, Integer> {
	
	/** Returns a collection of object arrays
	 * containing, in the first position, the multimedia
	 * and, in the second position, its average rating*/
	@Query("select mu, avg(mk.score) from Multimedia mu join mu.marks mk " +
			"group by mu " +
			"order by avg(mk.score) desc")
	Collection<Object[]> findOrderedByAverageScore();
	
	/** Query used in the search box, searchs by title, genres and tags*/
	@Query("select m from Multimedia m join m.tags t " +
			"where (m.title like concat(concat('%', ?1), '%')) " +
			"or (m.genres like concat(concat('%', ?1), '%')) " +
			"or (t like concat(concat('%', ?1), '%')) " +
			"group by m")
	Collection<Multimedia> findByKeyword(String keyword);
	
	/** Second part of the search box query.
	 *  This had to be separated from the query above because sometimes
	 *  a multimedia may contain no roles
	 *  and searching for r.staff.name may bring problems in that case*/
	@Query("select m from Multimedia m join m.roles r" +
			" where (concat(r.staff.name, concat(' ', r.staff.surname)) " +
			" like concat(concat('%', ?1), '%'))" +
			" group by m")
	Collection<Multimedia> findByStaffKeyword(String keyword);
}
