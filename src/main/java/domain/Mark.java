package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes={@Index(columnList="score"),
				@Index(columnList="isViewed")},
		uniqueConstraints = @UniqueConstraint(
			columnNames={"customer_id", "content_id"}
		)
	)
public class Mark extends DomainEntity {

	private boolean isViewed;
	private Integer score;
	
	// Getters & Setters
	
	public boolean getIsViewed() {
		return isViewed;
	}
	public void setIsViewed(boolean isViewed) {
		this.isViewed = isViewed;
	}
	
	@Range(min=0, max=5)
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	
	// Relationships
	private Customer customer;
	private Content content;

	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	@NotFound(action=NotFoundAction.IGNORE)
	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		this.content = content;
	}
}
