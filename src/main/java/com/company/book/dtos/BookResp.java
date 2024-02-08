package com.company.book.dtos;

import com.company.attach.dtos.AttachResp;
import com.company.category.dtos.CategoryResp;
import lombok.Builder;

import java.util.UUID;

@Builder
public record BookResp(
        UUID id,
        String title,
        String author,
        String description,
        CategoryResp category,
        AttachResp photo,
        AttachResp pdf
) {
}
