package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Film extends Multimedia {

	// Attributes -------------------------------------------------------------
	private int runningTime;
	private double budget;
	private double boxOffice;
	
	// Getters & Setters -------------------------------------------------------
	
	@Min(0)
	@NotNull
	public int getRunningTime() {
		return runningTime;
	}
	public void setRunningTime(int runningTime) {
		this.runningTime = runningTime;
	}
	
	@Digits(integer=12, fraction=2)
	@Min(0)
	public double getBudget() {
		return budget;
	}
	public void setBudget(double budget) {
		this.budget = budget;
	}
	
	@Digits(integer=12, fraction=2)
	@Min(0)
	public double getBoxOffice() {
		return boxOffice;
	}
	public void setBoxOffice(double boxOffice) {
		this.boxOffice = boxOffice;
	}
	
	// Relationships -----------------------------------------------------------
	
}
