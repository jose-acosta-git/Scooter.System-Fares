package fares.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fares.model.Fare;

public interface FareRepository extends JpaRepository<Fare, Integer>{

    @Query("SELECT f.standardPrice FROM Fare f WHERE f.startDate <= :today ORDER BY f.startDate DESC LIMIT 1")
    Optional<Double> findCurrentStandardPrice(@Param("today") LocalDate today);
    
    @Query("SELECT f.extendedPausePrice FROM Fare f WHERE f.startDate <= :today ORDER BY f.startDate DESC LIMIT 1")
    Optional<Double> findCurrentExtendedPausePrice(@Param("today") LocalDate today);
}