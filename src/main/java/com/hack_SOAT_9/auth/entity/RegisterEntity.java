package com.hack_SOAT_9.auth.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Random;
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
    private String username;

    public static String createUsername(String nome, String sobrenome) {
        nome = nome.trim().toLowerCase();
        sobrenome = sobrenome.trim().toLowerCase();

        String parteNome = nome.length() >= 3 ? nome.substring(0, 3) : nome;
        String parteSobrenome = sobrenome.length() >= 4 ? sobrenome.substring(0, 4) : sobrenome;

        String base = capitalize(parteNome + parteSobrenome);

        Random random = new Random();
        int randomNumber = 100 + random.nextInt(900);

        return base + randomNumber;
    }

    private static String capitalize(String str) {
        if (str.isEmpty()) return str;
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}
