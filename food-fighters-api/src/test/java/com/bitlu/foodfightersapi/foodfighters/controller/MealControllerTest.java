package com.bitlu.foodfightersapi.foodfighters.controller;

import com.bitlu.foodfightersapi.foodfighters.model.Meal;
import com.bitlu.foodfightersapi.foodfighters.repository.MealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class MealControllerTest {


    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    private MealController mealController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllMeals() {
        List<Meal> mealList = new ArrayList<>();
        mealList.add(new Meal());
        mealList.add(new Meal());

        when(mealRepository.findAll()).thenReturn(mealList);

        List<Meal> result = mealController.getAllMeals();

        assertEquals(mealList.size(), result.size());
        verify(mealRepository, times(1)).findAll();
    }

    @Test
    void testGetMealById() {
        Meal meal = new Meal();
        meal.setMealId("1");

        when(mealRepository.findById("1")).thenReturn(Optional.of(meal));

        ResponseEntity<Meal> responseEntity = mealController.getMealById("1");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(meal, responseEntity.getBody());
        verify(mealRepository, times(1)).findById("1");
    }

    @Test
    void testCreateMeal() {
        Meal meal = new Meal();

        when(mealRepository.save(meal)).thenReturn(meal);

        ResponseEntity<Meal> responseEntity = mealController.createMeal(meal);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(meal, responseEntity.getBody());
        verify(mealRepository, times(1)).save(meal);
    }

    @Test
    void testUpdateMeal() {
        Meal existingMeal = new Meal();
        existingMeal.setMealId("1");

        Meal updatedMeal = new Meal();

        when(mealRepository.findById("1")).thenReturn(Optional.of(existingMeal));
        when(mealRepository.save(updatedMeal)).thenReturn(updatedMeal);

        ResponseEntity<Meal> responseEntity = mealController.updateMeal("1", updatedMeal);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedMeal, responseEntity.getBody());
        verify(mealRepository, times(1)).findById("1");
        verify(mealRepository, times(1)).save(updatedMeal);
    }

    @Test
    void testDeleteMeal() {
        ResponseEntity<Meal> responseEntity = mealController.deleteMeal("1");

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(mealRepository, times(1)).deleteById("1");
    }
}