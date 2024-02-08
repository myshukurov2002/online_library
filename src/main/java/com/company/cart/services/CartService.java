package com.company.cart.services;

import com.company.base.ApiResponse;
import com.company.cart.dtos.CartCr;
import com.company.cart.dtos.CartResp;

import java.util.List;
import java.util.UUID;

public interface CartService {
    ApiResponse<CartResp> create(CartCr cartCr);


    ApiResponse<String> delete(UUID id);

    ApiResponse<CartResp> getById(UUID id);

    ApiResponse<List<CartResp>> getAllByOwner();


    ApiResponse<List<CartResp>> getAllPurchasedBooks();

    ApiResponse<CartResp> update(UUID id);
}
