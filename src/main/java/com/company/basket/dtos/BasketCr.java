package com.company.basket.dtos;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record BasketCr(
        @NotNull(message = "{cart.book.notNull}")
        UUID bookId
) {
}
