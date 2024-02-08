package com.company.category.dtos;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public record CategoryCr(
        @NotBlank(message = "{category.name.notBlank}") String name
) {
}
