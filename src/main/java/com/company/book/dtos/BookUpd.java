package com.company.book.dtos;

import java.math.BigDecimal;

public record BookUpd(
        String title,
        String author,
        BigDecimal price,
        String description
) {
}
