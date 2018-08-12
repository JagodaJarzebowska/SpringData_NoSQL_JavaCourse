package com.sda.model;

import lombok.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "userCollection")
public class User {

    public static final String NAME_FIELD = "name";
    public static final String SURNAME_FIELD = "surname";
    public static final String EMAIL_FIELD = "email";
    public static final String NICKNAME_FIELD = "nickname";

    @Id
    private ObjectId id;
    private String name;
    private String surename;
    private String nick;
    private String email;
    private String password;
    private StatusUser statusUser;
    private String textStatus;
    private Timestamp addTimeStamp;
    private Timestamp updateTimeStamp;


}
