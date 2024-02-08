package com.company.category.dtos;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CategoryResp(
        UUID id,
        String name
) {
}
