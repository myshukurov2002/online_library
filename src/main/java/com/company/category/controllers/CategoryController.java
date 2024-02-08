package com.company.category.controllers;

import com.company.base.ApiResponse;
import com.company.category.dtos.CategoryCr;
import com.company.category.dtos.CategoryResp;
import com.company.category.dtos.CategoryUpd;
import com.company.category.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CategoryResp>> create(
            @Valid @RequestBody CategoryCr category
    ) {
        return ResponseEntity.ok(categoryService.create(category));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update/{id}")
    @CachePut(cacheNames = "category", key = "#id")
    public ResponseEntity<ApiResponse<CategoryResp>> update(
            @RequestBody CategoryUpd category,
            @PathVariable("id") UUID id
    ) {
        return ResponseEntity.ok(categoryService.update(id, category));
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(cacheNames = "category", key = "#id")
    public ResponseEntity<ApiResponse<String>> delete(
            @PathVariable("id") UUID id
    ) {
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @GetMapping("/open/get-by-id/{id}")
    public ApiResponse<CategoryResp> getById(
            @PathVariable UUID id
    ) {
        return categoryService.getById(id);
    }

    @GetMapping("/open/get-all")
    public ApiResponse<List<CategoryResp>> getAll() {
        return categoryService.getAll();
    }

    @Scheduled(cron = "0 30 0 0 0 0")
    @CacheEvict(value = "category", allEntries = true)
    public void cleanCaches() {
//        log.info("cache category cleared");
    }
}
