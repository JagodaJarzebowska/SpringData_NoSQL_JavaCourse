package com.sda.repository;


import com.sda.model.StatusUser;
import com.sda.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    //7.2*
    List<User> findUserByNameOrSurenameOrNickOrEmail(String name, String surename, String nick, String email);

    //7.3
    List<User> findByStatusUser(StatusUser statusUser);

    //7.4
    Optional<User> findById(ObjectId id);

    //7.6
     void deleteById(ObjectId id);

     //7.7

}
