package fares.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;


import java.time.LocalDate;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;


import fares.model.Fare;

public interface FareRepository extends MongoRepository<Fare, String>{

    default Optional<Double> findCurrentStandardPrice(LocalDate today, MongoTemplate mongoTemplate) {
        AggregationOperation match = match(Criteria.where("startDate").lte(today));
        AggregationOperation sort = sort(Sort.by(Sort.Order.desc("startDate")));
        AggregationOperation limit = limit(1);
        AggregationOperation project = project("standardPrice").andExclude("_id");

        Aggregation aggregation = newAggregation(match, sort, limit, project);

        List<Fare> fares = mongoTemplate.aggregate(aggregation, Fare.class, Fare.class).getMappedResults();

        return fares.isEmpty() ? Optional.empty() : Optional.of(fares.get(0).getStandardPrice());
    }

    default Optional<Double> findCurrentExtendedPausePrice(LocalDate today, MongoTemplate mongoTemplate) {
        AggregationOperation match = match(Criteria.where("startDate").lte(today));
        AggregationOperation sort = sort(Sort.by(Sort.Order.desc("startDate")));
        AggregationOperation limit = limit(1);
        AggregationOperation project = project("extendedPausePrice").andExclude("_id");

        Aggregation aggregation = newAggregation(match, sort, limit, project);

        List<Fare> fares = mongoTemplate.aggregate(aggregation, Fare.class, Fare.class).getMappedResults();

        return fares.isEmpty() ? Optional.empty() : Optional.of(fares.get(0).getExtendedPausePrice());
    }

}