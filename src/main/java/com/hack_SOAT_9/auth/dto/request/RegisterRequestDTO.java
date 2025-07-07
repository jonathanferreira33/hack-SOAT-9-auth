package com.hack_SOAT_9.auth.dto.request;

public record RegisterRequestDTO (
    String firstName,
    String surname,
    String email,
    String password
) {}
