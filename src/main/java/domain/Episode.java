package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes={@Index(columnList="seasonNumber")})
public class Episode extends Content {

	// Attributes -------------------------------------------------------
	private String title;
	private int seasonNumber;
	private int episodeNumber;
	private int runningTime;
	
	// Getters & Setters ------------------------------------------------
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Min(0)
	public int getSeasonNumber() {
		return seasonNumber;
	}
	public void setSeasonNumber(int seasonNumber) {
		this.seasonNumber = seasonNumber;
	}
	@Min(0)
	public int getEpisodeNumber() {
		return episodeNumber;
	}
	public void setEpisodeNumber(int episodeNumber) {
		this.episodeNumber = episodeNumber;
	}
	@Min(0)
	public int getRunningTime() {
		return runningTime;
	}
	public void setRunningTime(int runningtime) {
		this.runningTime = runningtime;
	}
	
	// Relationships --------------------------------------------------------
	private TVSerie serie;
	private ProducerCompany producerCompany;
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public TVSerie getSerie() {
		return serie;
	}
	public void setSerie(TVSerie serie) {
		this.serie = serie;
	}
	
	@ManyToOne(optional=true)
	public ProducerCompany getProducerCompany() {
		ProducerCompany result;
		
		if(serie != null){
			result = serie.getProducerCompany();
		}else{
			result = producerCompany;
		}
		return result;
	}
	public void setProducerCompany(ProducerCompany producerCompany) {
		this.producerCompany = producerCompany;
	}
	
}
