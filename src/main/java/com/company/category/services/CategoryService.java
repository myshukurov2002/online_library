package com.company.category.services;

import com.company.base.ApiResponse;
import com.company.category.dtos.CategoryCr;
import com.company.category.dtos.CategoryResp;
import com.company.category.dtos.CategoryUpd;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    ApiResponse<CategoryResp> create(CategoryCr categoryCr);

    ApiResponse<CategoryResp> update(UUID id, CategoryUpd category);

    ApiResponse<String> delete(UUID id);

    ApiResponse<List<CategoryResp>> getAll();

    ApiResponse<CategoryResp> getById(UUID id);
}
