package fares.dtos;

import java.time.LocalDate;

public class FareDto {
	
	private double standardPrice;
	private double extendedPausePrice;
	private LocalDate startDate;
	
	public FareDto(double standardPrice, double extendedPausePrice, LocalDate startDate) {
		this.standardPrice = standardPrice;
		this.extendedPausePrice = extendedPausePrice;
		this.startDate = startDate;
	}

	public double getStandardPrice() {return standardPrice;}
	public double getExtendedPausePrice() {return extendedPausePrice;}
	public LocalDate getStartDate() {return startDate;}
}