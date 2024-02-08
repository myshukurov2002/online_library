package com.company.cart.dtos;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record CartCr(
        @NotNull(message = "{cart.book.notNull}")
        UUID bookId
) {
}
