package com.hack_SOAT_9.auth.dto.response;

public record LoginResponseDTO(
        String token,
        String userName,
        String userID
) {}
