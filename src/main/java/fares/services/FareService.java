package fares.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fares.dtos.FareDto;
import fares.model.Fare;
import fares.repositories.FareRepository;

@Service
public class FareService {
	
	@Autowired
	private FareRepository fareRepository;

	public ResponseEntity<Fare> save(FareDto dto) {
		LocalDate today = LocalDate.now();
		LocalDate startDate = dto.getStartDate();
		if (startDate.isAfter(today) || startDate.isEqual(today)) {
			Fare fare = convertToEntity(dto);
			return ResponseEntity.ok(fareRepository.save(fare));
		}
		return ResponseEntity.badRequest().build();
	}
	
	public ResponseEntity<Double> getCurrentStandardPrice() {
	    LocalDate today = LocalDate.now();
	    Optional<Double> currentStandardPriceOptional = fareRepository.findCurrentStandardPrice(today);
	    if (currentStandardPriceOptional.isPresent()) {
	    	return ResponseEntity.ok(currentStandardPriceOptional.get());
	    }
	    return ResponseEntity.notFound().build();
	}
	
	public ResponseEntity<Double> getCurrentExtendedPausePrice() {
	    LocalDate today = LocalDate.now();
	    Optional<Double> currentExtendedPausePriceOptional = fareRepository.findCurrentExtendedPausePrice(today);
	    if (currentExtendedPausePriceOptional.isPresent()) {
	    	return ResponseEntity.ok(currentExtendedPausePriceOptional.get());
	    }
	    return ResponseEntity.notFound().build();
	}

	private Fare convertToEntity(FareDto dto) {
		return new Fare(dto.getStandardPrice(), dto.getExtendedPausePrice(), dto.getStartDate());
	}
}