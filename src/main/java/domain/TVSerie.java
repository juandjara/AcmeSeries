package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class TVSerie extends Multimedia {

	// Attributes -------------------------------------------------------------
	private String originalChannel;
	
	// Getters & Setters ------------------------------------------------------
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getOriginalChannel() {
		return originalChannel;
	}
	public void setOriginalChannel(String originalChannel) {
		this.originalChannel = originalChannel;
	}
	
	// Relationships ---------------------------------------------------------
	private Collection<Episode> episodes;

	@NotNull
	@Valid
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="serie")
	public Collection<Episode> getEpisodes() {
		return episodes;
	}
	public void setEpisodes(Collection<Episode> episodes) {
		this.episodes = episodes;
	}
	
	
}
