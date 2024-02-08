package com.company.auth.dtos;

import com.company.user.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record JwtResponse(
        UUID id,
        List<Role> roles,
        String token
) {
}

