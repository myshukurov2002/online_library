package com.company.auth.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RegistrDto(
        @NotBlank(message = "{firstName.notBlank}") String firstName,
        @NotBlank(message = "{lastName.notBlank}") String lastName,
        @NotBlank(message = "{email.notBlank}")
        @Email String email,
        @NotBlank(message = "{password.notBlank}") String password
) {
}
