package fares.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Fare {
	
	@Id @GeneratedValue (strategy = GenerationType.AUTO)
	private int id;
	
	@Column
	private double standardPrice;
	
	@Column
	private double extendedPausePrice;
	
	@Column
	private LocalDate startDate;

	public Fare(double standardPrice, double extendedPausePrice, LocalDate startDate) {
		this.standardPrice = standardPrice;
		this.extendedPausePrice = extendedPausePrice;
		this.startDate = startDate;
	}
	
	public Fare() {}

	public int getId() {return id;}
	public double getStandardPrice() {return standardPrice;}
	public double getExtendedPausePrice() {return extendedPausePrice;}
	public LocalDate getStartDate() {return startDate;}
}