package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;
import domain.Content;
import domain.Customer;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	@Query("select c from Comment c where c.children.size = "+
		   "(select max(c2.children.size) from Comment c2)" +
		   "and c.sender = ?1")
	Collection<Comment> commentsWithMoreRepliesByCustomer(Customer customer);
	
	@Query("select c from Comment c where c.content = ?1 " +
			"order by c.creationDate")
	Collection<Comment> findByContentOrderedByDate(Content content);
	
	@Query("select c from Comment c where c.parent = ?1 " +
			"order by c.creationDate")
	Collection<Comment> findByParentOrderedByDate(Comment parent);
	
	@Query("select c from Comment c where c.sender = ?1 " +
			"order by c.creationDate")
	Collection<Comment> findBySenderOrderedByDate(Customer sender);
	
	@Query("select c from Comment c where c.receiver = ?1 " +
			"order by c.creationDate")
	Collection<Comment> findByReceiverOrderedByDate(Content receiver);
		
}
