package com.company.book.dtos;

import java.util.UUID;

public record BookCr(
        String title,
        String author,
        String description,
        UUID categoryId,
        UUID photoId,
        UUID pdfId
) {
}
