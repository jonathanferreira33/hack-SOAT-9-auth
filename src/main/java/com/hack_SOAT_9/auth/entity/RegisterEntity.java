package com.hack_SOAT_9.auth.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "tb_register")
public class RegisterEntity {

    @MongoId
    @Builder.Default
    private String userId = UUID.randomUUID().toString();

    private String email;
    private String name;
    private String surname;
    private String password;
}
