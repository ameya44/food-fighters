package com.bitlu.foodfightersapi.foodfighters.repository;

import com.bitlu.foodfightersapi.foodfighters.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {

}
