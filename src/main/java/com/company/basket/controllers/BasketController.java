package com.company.basket.controllers;

import com.company.base.ApiResponse;
import com.company.basket.dtos.BasketCr;
import com.company.basket.dtos.BasketResp;
import com.company.basket.services.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/basket")
public class BasketController {

    private final BasketService basketService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<BasketResp>> create(
            @Valid @RequestBody BasketCr basketCr
    ) {
        return ResponseEntity.ok(basketService.create(basketCr));
    }

    @PutMapping("/buy/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<BasketResp>> update(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(basketService.update(id));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<String>> delete(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(basketService.delete(id));
    }

    @GetMapping("/get-by-id/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<BasketResp>> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(basketService.getById(id));
    }

    @GetMapping("/get-cart-books")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<List<BasketResp>>> getAllByOwner() {
        return ResponseEntity.ok(basketService.getAllByOwner());
    }


    @GetMapping("/get-purchased-books")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<List<BasketResp>>> getAllPurchasedBooks() {
        return ResponseEntity.ok(basketService.getAllPurchasedBooks());
    }


}
