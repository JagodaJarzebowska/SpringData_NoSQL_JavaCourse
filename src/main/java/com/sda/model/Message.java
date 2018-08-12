package com.sda.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "messageCollections")
public class Message {

    @Id
    private ObjectId id;
    private String sender;
    private String recipient;
    private String content;
    private MessageType messageType;
    private Status status;
    private Timestamp timestamp;
}
