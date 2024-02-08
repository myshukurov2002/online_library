package com.company.user.dtos;

import com.company.user.enums.Role;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record RoleCr(
        @NotNull(message = "USER should not be null")
        UUID userId,
        @NotNull(message = "ROLE should not be null")
        List<Role> roles
) {
}
