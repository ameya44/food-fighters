package com.bitlu.foodfightersapi.foodfighters.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection="Meals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meal {

    @Id
    String mealId;

    @DBRef
    @Field("userId")
    String userId;

    String mealType;
    Date createdAt;
    Date updatedAt;

}
