package fares.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fares.dtos.FareDto;
import fares.model.Fare;
import fares.services.FareService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fares")
public class FareController {

	@Autowired
	private FareService fareService;
	
	@PostMapping
	public ResponseEntity<Fare> create(HttpServletRequest request, @RequestBody FareDto dto) {
		return fareService.save(request, dto);
	}
	
	@GetMapping("/currentStandardPrice")
	public ResponseEntity<Double> getCurrentStandardPrice(HttpServletRequest request) {
		return fareService.getCurrentStandardPrice(request);
	}
	
	@GetMapping("/currentExtendedPausePrice")
	public ResponseEntity<Double> getCurrentExtendedPausePrice(HttpServletRequest request) {
		return fareService.getCurrentExtendedPausePrice(request);
	}
    
    @GetMapping
    public ResponseEntity<List<Fare>> findAll(HttpServletRequest request) {
		return fareService.findAll(request);
    }
}