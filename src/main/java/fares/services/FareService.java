package fares.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fares.dtos.FareDto;
import fares.model.Fare;
import fares.repositories.FareRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class FareService {
	
	@Autowired
	private FareRepository fareRepository;
	@Autowired
	private AuthService authService;
	@Autowired
	MongoTemplate mongoTemplate;

	public ResponseEntity<Fare> save(HttpServletRequest request, FareDto dto) {
		String token = authService.getTokenFromRequest(request);
		if (token == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		String role = authService.getRole(token);
		if (role == null || !role.equals("ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		LocalDate today = LocalDate.now();
		LocalDate startDate = dto.getStartDate();
		if (startDate.isAfter(today) || startDate.isEqual(today)) {
			Fare fare = convertToEntity(dto);
			return ResponseEntity.ok(fareRepository.save(fare));
		}
		return ResponseEntity.badRequest().build();
	}
	
	public ResponseEntity<Double> getCurrentStandardPrice(HttpServletRequest request) {
		String token = authService.getTokenFromRequest(request);
		if (token == null || !authService.isTokenValid(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

	    LocalDate today = LocalDate.now();
	    Optional<Double> currentStandardPriceOptional = fareRepository.findCurrentStandardPrice(today, mongoTemplate);
	    if (currentStandardPriceOptional.isPresent()) {
	    	return ResponseEntity.ok(currentStandardPriceOptional.get());
	    }
	    return ResponseEntity.notFound().build();
	}
	
	public ResponseEntity<Double> getCurrentExtendedPausePrice(HttpServletRequest request) {
		String token = authService.getTokenFromRequest(request);
		if (token == null || !authService.isTokenValid(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

	    LocalDate today = LocalDate.now();
	    Optional<Double> currentExtendedPausePriceOptional = fareRepository.findCurrentExtendedPausePrice(today, mongoTemplate);
	    if (currentExtendedPausePriceOptional.isPresent()) {
	    	return ResponseEntity.ok(currentExtendedPausePriceOptional.get());
	    }
	    return ResponseEntity.notFound().build();
	}

	private Fare convertToEntity(FareDto dto) {
		return new Fare(dto.getStandardPrice(), dto.getExtendedPausePrice(), dto.getStartDate());
	}

	public ResponseEntity<List<Fare>> findAll(HttpServletRequest request) {
		String token = authService.getTokenFromRequest(request);
		if (token == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		String role = authService.getRole(token);
		if (role == null || !role.equals("ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		return ResponseEntity.ok(fareRepository.findAll());
	}
}