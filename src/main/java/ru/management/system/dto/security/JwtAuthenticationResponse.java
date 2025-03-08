package ru.management.system.dto.security;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с токеном JWT")
public record JwtAuthenticationResponse(
        @Schema(description = "Токен доступа JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.ey...")
        String token
) {
}
