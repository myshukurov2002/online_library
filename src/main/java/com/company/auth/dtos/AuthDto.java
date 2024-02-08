package com.company.auth.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthDto(
        @NotNull(message = "{phone.notNull}")
        @NotBlank(message = "{email.notBlank}")
        String email,

        @NotNull(message = "{password.notNull}")
        @NotBlank(message = "{password.notBlank}")
        @Size(min = 6, message = "{password.size}")
        String password
) {
}
