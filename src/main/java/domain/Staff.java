package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes={@Index(columnList="name"),
				@Index(columnList="surname")})
public class Staff extends DomainEntity{

	// Attributes ---------------------------------------------------------
	private String name;
	private String surname;
	private String email;
	
	// Getters & Setters --------------------------------------------------
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getName() {
		return name;
	}
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getSurname() {
		return surname;
	}
	@NotBlank
	@Email
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getEmail() {
		return email;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	// Relationships --------------------------------------------------------
	private Collection<Role> roles;
	private ProducerCompany producer;
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="staff")
	public Collection<Role> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public ProducerCompany getProducer() {
		return producer;
	}
	public void setProducer(ProducerCompany producer) {
		this.producer = producer;
	}
	@Transient
	public String getFullname() {
		return getName()+" "+getSurname();
	}
	public void setFullname(String fullname){}
}
