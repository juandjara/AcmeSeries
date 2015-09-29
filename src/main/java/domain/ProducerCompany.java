package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class ProducerCompany extends Actor {

	// Attributes -----------------------------------------------------
	private Date fundationDate;
	private String webpage;
	
	// Getters & Setters ----------------------------------------------
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Past
	public Date getFundationDate() {
		return fundationDate;
	}
	public void setFundationDate(Date fundationDate) {
		this.fundationDate = fundationDate;
	}
	
	@URL
	public String getWebpage() {
		return webpage;
	}
	public void setWebpage(String webpage) {
		this.webpage = webpage;
	}
	
	// Relationships --------------------------------------------------
	private Collection<Multimedia> multimedias;

	@NotNull
	@Valid
	@OneToMany(mappedBy="producerCompany")
	public Collection<Multimedia> getMultimedias() {
		return multimedias;
	}
	public void setMultimedias(Collection<Multimedia> multimedias) {
		this.multimedias = multimedias;
	}
	
}
