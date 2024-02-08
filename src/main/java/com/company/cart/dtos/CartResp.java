package com.company.cart.dtos;

import com.company.book.dtos.BookResp;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import java.util.UUID;
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CartResp(
        UUID id,
        BookResp book
) {
}
