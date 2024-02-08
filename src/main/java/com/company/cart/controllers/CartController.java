package com.company.cart.controllers;

import com.company.base.ApiResponse;
import com.company.cart.dtos.CartCr;
import com.company.cart.dtos.CartResp;
import com.company.cart.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<CartResp>> create(
            @Valid @RequestBody CartCr cartCr
    ) {
        return ResponseEntity.ok(cartService.create(cartCr));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<CartResp>> update(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(cartService.update(id));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<String>> delete(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(cartService.delete(id));
    }

    @GetMapping("/get-by-id/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<CartResp>> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(cartService.getById(id));
    }

    @GetMapping("/get-cart-books")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<List<CartResp>>> getAllByOwner() {
        return ResponseEntity.ok(cartService.getAllByOwner());
    }


    @GetMapping("/get-purchased-books")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<List<CartResp>>> getAllPurchasedBooks() {
        return ResponseEntity.ok(cartService.getAllPurchasedBooks());
    }


}
