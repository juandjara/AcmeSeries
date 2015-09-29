package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes={@Index(columnList="title"),
				@Index(columnList="genres")})
public abstract class Multimedia extends Content {

	// Attributes -------------------------------------------------------------
	private String title;
	private String genres;
	private String sinopsis;
	private Date releaseDate;
	private String distributionCompany;
	private String originCountry;
	private Collection<String> tags;
	
	// Getters&Setters ----------------------------------------------------
	
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getTitle() {
		return title;
	}
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getGenres() {
		return genres;
	}
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getSinopsis() {
		return sinopsis;
	}
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getReleaseDate() {
		return releaseDate;
	}
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getDistributionCompany() {
		return distributionCompany;
	}
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getOriginCountry() {
		return originCountry;
	}
	
	@NotNull
	@ElementCollection
	@OrderColumn(name="tag_index") // this annotation optimizes the querying of this field
	public Collection<String> getTags() {
		return tags;
	}
	public void setTags(Collection<String> tags) {
		this.tags = tags;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setGenres(String genres) {
		this.genres = genres;
	}
	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public void setDistributionCompany(String distributionCompany) {
		this.distributionCompany = distributionCompany;
	}
	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}
	
	// Relationships ----------------------------------------------------------
	private ProducerCompany producerCompany;
	private Collection<Role> roles;
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public ProducerCompany getProducerCompany() {
		return producerCompany;
	}
	public void setProducerCompany(ProducerCompany producerCompany) {
		this.producerCompany = producerCompany;
	}
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="multimedia")
	public Collection<Role> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	
}
