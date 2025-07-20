package com.bitlu.foodfightersapi.foodfighters.controller;

import com.bitlu.foodfightersapi.foodfighters.service.ExternalFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/external-foods")
public class ExternalFoodController {

    @Autowired
    private ExternalFoodService externalFoodService;

    @GetMapping("/search")
    public String search(@RequestParam String query) {
        return externalFoodService.searchFoods(query);
    }
}
