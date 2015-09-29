package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Role extends DomainEntity{

	// Attributes -----------------------------------------------------------------
	private String role;

	// Getters & Setters ----------------------------------------------------------
	@NotBlank
	@Pattern(regexp = "^WRITER|DIRECTOR|COMPOSER|ACTOR$")
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	// Relationships --------------------------------------------------------------
	private Staff staff;
	private Multimedia multimedia;
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Staff getStaff() {
		return staff;
	}
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Multimedia getMultimedia() {
		return multimedia;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public void setMultimedia(Multimedia multimedia) {
		this.multimedia = multimedia;
	}
	
}
