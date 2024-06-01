package com.bitlu.foodfightersapi.foodfighters.controller;

import com.bitlu.foodfightersapi.foodfighters.model.FoodItem;
import com.bitlu.foodfightersapi.foodfighters.repository.FoodItemRepository;
import com.bitlu.foodfightersapi.foodfighters.repository.FoodItemRepository;
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

class FoodItemControllerTest {


    @Mock
    private FoodItemRepository foodItemRepository;

    @InjectMocks
    private FoodItemController foodItemController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllFoodItems() {
        List<FoodItem> foodItemList = new ArrayList<>();
        foodItemList.add(new FoodItem());

        when(foodItemRepository.findAll()).thenReturn(foodItemList);

        List<FoodItem> result = foodItemController.getAllFoodItems();

        assertEquals(foodItemList.size(), result.size());
        verify(foodItemRepository, times(1)).findAll();
    }

    @Test
    void testGetFoodItemById() {
        FoodItem foodItem = new FoodItem();
        foodItem.setFoodId("1");

        when(foodItemRepository.findById("1")).thenReturn(Optional.of(foodItem));

        ResponseEntity<FoodItem> responseEntity = foodItemController.getFoodItemById("1");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(foodItem, responseEntity.getBody());
        verify(foodItemRepository, times(1)).findById("1");
    }

    @Test
    void testCreateFoodItem() {
        FoodItem foodItem = new FoodItem();

        when(foodItemRepository.save(foodItem)).thenReturn(foodItem);

        ResponseEntity<FoodItem> responseEntity = foodItemController.createFoodItem(foodItem);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(foodItem, responseEntity.getBody());
        verify(foodItemRepository, times(1)).save(foodItem);
    }

    @Test
    void testUpdateFoodItem() {
        FoodItem existingFoodItem = new FoodItem();
        existingFoodItem.setFoodId("1");

        FoodItem updatedFoodItem = new FoodItem();

        when(foodItemRepository.findById("1")).thenReturn(Optional.of(existingFoodItem));
        when(foodItemRepository.save(updatedFoodItem)).thenReturn(updatedFoodItem);

        ResponseEntity<FoodItem> responseEntity = foodItemController.updateFoodItem("1", updatedFoodItem);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedFoodItem, responseEntity.getBody());
        verify(foodItemRepository, times(1)).findById("1");
        verify(foodItemRepository, times(1)).save(updatedFoodItem);
    }

    @Test
    void testDeleteFoodItem() {
        ResponseEntity<FoodItem> responseEntity = foodItemController.deleteFoodItem("1");

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(foodItemRepository, times(1)).deleteById("1");
    }
}