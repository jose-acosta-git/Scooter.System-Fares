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
import fares.repositories.FareRepository;
import fares.services.FareService;

@RestController
@RequestMapping("/fares")
public class FareController {

	@Autowired
	private FareRepository fareRepository;
	@Autowired
	private FareService fareService;
	
	@PostMapping
	public ResponseEntity<Fare> create(@RequestBody FareDto dto) {
		return fareService.save(dto);
	}
	
	@GetMapping("/currentStandardPrice")
	public ResponseEntity<Double> getCurrentStandardPrice() {
		return fareService.getCurrentStandardPrice();
	}
	
	@GetMapping("/currentExtendedPausePrice")
	public ResponseEntity<Double> getCurrentExtendedPausePrice() {
		return fareService.getCurrentExtendedPausePrice();
	}
    
    @GetMapping
    public List<Fare> findAll() {
    	return fareRepository.findAll();
    }
}