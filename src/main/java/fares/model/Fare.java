package fares.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("fares")
public class Fare {
	
	@Id
	private String id;
	
	private double standardPrice;
	private double extendedPausePrice;
	private LocalDate startDate;

	public Fare(double standardPrice, double extendedPausePrice, LocalDate startDate) {
		this.standardPrice = standardPrice;
		this.extendedPausePrice = extendedPausePrice;
		this.startDate = startDate;
	}
	
	public Fare() {}

	public String getId() {return id;}
	public double getStandardPrice() {return standardPrice;}
	public double getExtendedPausePrice() {return extendedPausePrice;}
	public LocalDate getStartDate() {return startDate;}
}