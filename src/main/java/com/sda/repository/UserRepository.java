package com.sda.repository;


import com.sda.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    List<User> findUserByNameOrSurenameOrNickOrEmail(String name);

}
