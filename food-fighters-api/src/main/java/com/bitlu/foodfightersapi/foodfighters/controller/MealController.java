package com.bitlu.foodfightersapi.foodfighters.controller;

import com.bitlu.foodfightersapi.foodfighters.model.Meal;
import com.bitlu.foodfightersapi.foodfighters.repository.MealRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/meal")
@Slf4j
public class MealController {

    @Autowired
    private MealRepository mealRepository;

    @GetMapping
    public List<Meal> getAllMeals(){
        return mealRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meal> getMealById(@PathVariable String id) {
        log.info("Finding Meal by ID {}", id);
        Optional<Meal> mealOptional = mealRepository.findById(id);
        return mealOptional.map(meal -> new ResponseEntity<>(meal, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Meal> createMeal(@RequestBody Meal meal) {
        log.info("Saving Meal  {}", meal);
        meal.setUpdatedAt(new Date());
        Meal savedMeal = mealRepository.save(meal);
        return new ResponseEntity<>(savedMeal, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Meal> updateMeal(@PathVariable String id, @RequestBody Meal meal) {
        Optional<Meal> mealEdit = mealRepository.findById(id);
        if (mealEdit.isPresent()) {

            meal.setMealId(id);
            meal.setUpdatedAt(new Date());
            mealRepository.save(meal);
            return new ResponseEntity<>(meal, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Meal> deleteMeal(@PathVariable String id) {
        log.info("Deleting Meal  {}", id);
        mealRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
