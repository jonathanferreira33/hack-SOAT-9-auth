package com.hack_SOAT_9.auth.repository;

import com.hack_SOAT_9.auth.entity.RegisterEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RegisterRepository repository;

    @Override
    public void run(String... args) {
        RegisterEntity entity = RegisterEntity.builder()
                .email("a")
                .name("b")
                .surname("c")
                .password("d")
                .build();
        repository.save(entity);
    }
}
