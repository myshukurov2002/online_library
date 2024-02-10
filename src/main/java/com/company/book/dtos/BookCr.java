package com.company.book.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record BookCr(
        String title,
        String author,
        String description,
        BigDecimal price,
        UUID categoryId,
        UUID photoId,
        UUID pdfId
) {
}
