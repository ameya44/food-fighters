package com.bitlu.foodfightersapi.foodfighters.repository;

import com.bitlu.foodfightersapi.foodfighters.model.FoodItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodItemRepository extends MongoRepository<FoodItem,String> {
}
