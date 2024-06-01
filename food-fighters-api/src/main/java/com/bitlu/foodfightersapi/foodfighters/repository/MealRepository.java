package com.bitlu.foodfightersapi.foodfighters.repository;

import com.bitlu.foodfightersapi.foodfighters.model.Meal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MealRepository extends MongoRepository<Meal,String> {
}

