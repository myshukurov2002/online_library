package com.company.basket.services;

import com.company.base.ApiResponse;
import com.company.basket.dtos.BasketCr;
import com.company.basket.dtos.BasketResp;

import java.util.List;
import java.util.UUID;

public interface BasketService {
    ApiResponse<BasketResp> create(BasketCr basketCr);

    ApiResponse<String> delete(UUID id);

    ApiResponse<BasketResp> getById(UUID id);

    ApiResponse<List<BasketResp>> getAllByOwner();

    ApiResponse<List<BasketResp>> getAllPurchasedBooks();

    ApiResponse<BasketResp> update(UUID id);
}
