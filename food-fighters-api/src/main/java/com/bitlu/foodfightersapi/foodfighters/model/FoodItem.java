package com.bitlu.foodfightersapi.foodfighters.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection="FoodItems")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItem {

    @Id
    String foodId;

    @DBRef
    @Field("mealId")
    String mealId;

    String name;
    Integer quantity;
    Integer calories;
    Date createdAt;
    Date updatedAt;

}
