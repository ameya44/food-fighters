package com.bitlu.foodfightersapi.foodfighters.controller;

import com.bitlu.foodfightersapi.foodfighters.model.FoodItem;
import com.bitlu.foodfightersapi.foodfighters.repository.FoodItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/foodItem")
@Slf4j
public class FoodItemController {

    @Autowired
    private FoodItemRepository foodItemRepository;

    @GetMapping
    public List<FoodItem> getAllFoodItems() {
      return  foodItemRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodItem> getFoodItemById(@PathVariable String id) {
        log.info("Finding FoodItem by ID {}", id);
        Optional<FoodItem> foodItemOptional = foodItemRepository.findById(id);
        return foodItemOptional.map(food -> new ResponseEntity<>(food, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<FoodItem> createFoodItem(@RequestBody FoodItem food) {
        log.info("Saving FoodItem  {}", food);
        food.setUpdatedAt(new Date());
        FoodItem savedFood = foodItemRepository.save(food);
        return new ResponseEntity<>(savedFood, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<FoodItem> updateFoodItem(@PathVariable String id, @RequestBody FoodItem food) {
    Optional<FoodItem> foodEdit = foodItemRepository.findById(id);
        if (foodEdit.isPresent()) {

            food.setFoodId(id);
            food.setUpdatedAt(new Date());
            foodItemRepository.save(food);
            return new ResponseEntity<>(food, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FoodItem> deleteFoodItem(@PathVariable String id) {
        log.info("Deleting FoodItem  {}", id);
        foodItemRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}