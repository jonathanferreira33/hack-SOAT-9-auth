package com.hack_SOAT_9.auth.entity;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterEntityTest {

    @Test
    void shouldGenerateUsernameWithNameAndSurnameParts() {
        String username = RegisterEntity.createUsername("Jonathan", "Ferreira");

        assertThat(username)
                .startsWith("Jonferr")
                .hasSizeGreaterThan(6)
                .matches("[A-Z][a-z]{5,}\\d{3}"); // corrigido aqui
    }

    @Test
    void shouldCapitalizeFirstLetterOnly() {
        String username = RegisterEntity.createUsername("jon", "jons");

        assertThat(username)
                .startsWith("Jonjon")
                .matches("[A-Z][a-zA-Z]+\\d{3}");
    }

    @RepeatedTest(10)
    void shouldGenerateDifferentUsernamesDueToRandomNumber() {
        String user1 = RegisterEntity.createUsername("Jonathan", "Ferreira");
        String user2 = RegisterEntity.createUsername("Jonathan", "Ferreira");

        assertThat(user1).isNotEqualTo(user2);
    }
}
