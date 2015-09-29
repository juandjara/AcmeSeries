package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Content extends DomainEntity {

	// Attributes -----------------------------------------------------------------
	// Getters & Setters ----------------------------------------------------------
	// Relationships
	
	private Collection<Comment> comments;
	private Collection<Mark> marks;
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="content")
	public Collection<Comment> getComments() {
		return comments;
	}
	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="content")
	public Collection<Mark> getMarks() {
		return marks;
	}
	public void setMarks(Collection<Mark> marks) {
		this.marks = marks;
	}
	
}
