package com.sda.repository;

import com.sda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sda.model.User.*;

@Repository
public class UserDetailsRepository {

    private MongoTemplate mongoTemplate;

    @Autowired
    public UserDetailsRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<User> findBy(String name, String surname, String nick, String email) {
        Query query = new Query();
        if (name != null) {
            query.addCriteria(Criteria.where(NAME_FIELD).is(name));
        }

        if (surname != null) {
            query.addCriteria(Criteria.where(SURNAME_FIELD).is(surname));
        }

        if (nick != null) {
            query.addCriteria(Criteria.where(NICKNAME_FIELD).is(nick));
        }

        if (email != null) {
            query.addCriteria(Criteria.where(EMAIL_FIELD).is(email));
        }
        return mongoTemplate.find(query, User.class);
    }
}
