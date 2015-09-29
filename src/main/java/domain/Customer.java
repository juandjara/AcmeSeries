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
public class Customer extends Actor {

	// Attributes
	
	private boolean isBanned;
	
	// Getters & Setters
	
	public boolean getIsBanned() {
		return isBanned;
	}
	public void setIsBanned(boolean isBanned) {
		this.isBanned = isBanned;
	}
	
	// Relationships
	
	private Collection<Mark> marks;
	private Collection<Comment> comments;
	private Collection<Comment> recomendations;
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="customer")
	public Collection<Mark> getMarks() {
		return marks;
	}
	public void setMarks(Collection<Mark> marks) {
		this.marks = marks;
	}
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="sender")
	public Collection<Comment> getComments() {
		return comments;
	}
	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="receiver")
	public Collection<Comment> getRecomendations() {
		return recomendations;
	}
	public void setRecomendations(Collection<Comment> recomendations) {
		this.recomendations = recomendations;
	}
	
}
