package com.company.user.dtos;

import com.company.user.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResp(
        UUID id,
        String firstName,
        String lastName,
        String phone,
        String password,
        String address,
        Set<Role> roles
) {
}
