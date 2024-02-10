package com.company.basket.dtos;

import com.company.book.dtos.BookResp;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import java.util.UUID;
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BasketResp(
        UUID id,
        BookResp book
) {
}
