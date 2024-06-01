package com.bitlu.foodfightersapi.foodfighters.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection="Users")
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String userId;
    private String username;
    private String email;
    private String password;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;


    public User(String user1, String mail, String password1) {
    }
}
