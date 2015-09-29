package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Content;
import domain.ProducerCompany;

@Repository
public interface ContentRepository extends JpaRepository<Content, Integer> {
	
	@Query("select c from Content c where c.producerCompany = ?1 " +
			"and c.comments.size = "+
			"(select max(c2.comments.size) from Content c2 " +
			" where c2.producerCompany = ?1)")
	Collection<Content> FindMostCommentedByProducerCompany(ProducerCompany producer);
	
	/** Returns a collection of arrays.
	 * Every array in the collection contains, in the first position, a content
	 * and, in the second position, its average rating*/
	@Query("select c, avg(mk.score) from Content c join c.marks mk " +
			"group by c " +
			"order by avg(mk.score) desc")
	Collection<Object[]> findOrderedByAverageScore();
	
	/** Returns a collection of arrays.
	 * Every array in the collection contains, in the first position, a content
	 * and, in the second position, its average rating.
	 * Filters by producerCompany too*/
	@Query("select c, avg(mk.score) from Content c join c.marks mk " +
			"where c.producerCompany = ?1 " +
			"group by c " +
			"order by avg(mk.score) desc")
	Collection<Object[]> finbByProducerOrderedByAverageScore(ProducerCompany producer);
	
	/** Custom findOne using a query due to problems with
	 * retrieving a film or tv serie as Content */
	@Query("select c from Content c where c.id = ?1")
	Content customFindOne(int contentId);
	
	/** Query used to fix a bug finding mark.content
	 * where content is not an episode */
	@Query("select m.content from Mark m where m.id = ?1")
	Content findByMark(int markId);
}
