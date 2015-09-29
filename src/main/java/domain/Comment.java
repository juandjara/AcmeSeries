package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

	// Attributes ------------------------------------------------
	private String text;
	private Date creationDate;
	
	// Getters & Setters -----------------------------------------
	
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.BASIC)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Past
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	// Relationships
	private Customer sender;
	private Customer receiver;
	private Comment parent;
	private Collection<Comment> children;
	private Content content;

	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Customer getSender() {
		return sender;
	}
	public void setSender(Customer sender) {
		this.sender = sender;
	}
	
	@Valid
	@ManyToOne(optional=true)
	public Customer getReceiver() {
		return receiver;
	}
	public void setReceiver(Customer receiver) {
		this.receiver = receiver;
	}
	
	@Valid
	@ManyToOne(optional=true)
	public Comment getParent() {
		return parent;
	}
	public void setParent(Comment parent) {
		this.parent = parent;
	}
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="parent")
	public Collection<Comment> getChildren() {
		return children;
	}
	public void setChildren(Collection<Comment> children) {
		this.children = children;
	}
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		this.content = content;
	}
	
}
