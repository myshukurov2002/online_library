package com.company.category.services;

import com.company.base.ApiResponse;
import com.company.category.dtos.CategoryCr;
import com.company.category.dtos.CategoryResp;
import com.company.category.dtos.CategoryUpd;
import com.company.category.entities.CategoryEntity;
import com.company.category.repositories.CategoryRepository;
import com.company.config.i18n.MessageService;
import com.company.expections.exp.DuplicateItemException;
import com.company.expections.exp.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MessageService messageService;

    @Override
    public ApiResponse<CategoryResp> create(CategoryCr categoryCr) {

        categoryRepository
                .findByNameAndVisibilityTrue(categoryCr.name())
                .ifPresent(c -> {
                    throw new DuplicateItemException();
                });

        CategoryEntity category = toEntity(categoryCr);
        categoryRepository.save(category);

        log.info("category added id: " + category.getId());

        return new ApiResponse<>(true, toDto(category));
    }

    @Override
    public ApiResponse<CategoryResp> update(UUID id, CategoryUpd categoryUpd) {

        categoryRepository
                .findByNameAndVisibilityTrue(categoryUpd.name())
                .ifPresent(c -> {
                    throw new DuplicateItemException();
                });

        CategoryEntity category = get(id);

        category.setName(categoryUpd.name());
        categoryRepository.save(category);

        log.info("category updated id: " + category.getId());

        return new ApiResponse<>(true, toDto(category));
    }

    @Override
    public ApiResponse<String> delete(UUID id) {

        CategoryEntity category = get(id);

        category.setVisibility(false);
        categoryRepository.save(category);

        log.info("category deleted id: " + category.getId());

        return new ApiResponse<>(true, messageService.getMessage("success.deleted"));
    }

    @Override
    public ApiResponse<List<CategoryResp>> getAll() {

        List<CategoryResp> categories = categoryRepository
                .findAllByVisibilityTrue()
                .stream()
                .map(this::toDto)
                .toList();

        return new ApiResponse<>(true, categories);
    }

    @Override
    public ApiResponse<CategoryResp> getById(UUID id) {

        return new ApiResponse<>(true, toDto(get(id)));
    }

    private CategoryEntity get(UUID id) {

        return categoryRepository
                .findByIdAndVisibilityTrue(id)
                .orElseThrow(ItemNotFoundException::new);
    }

    private CategoryEntity toEntity(CategoryCr categoryCr) {

        return CategoryEntity.builder()
                .name(categoryCr.name())
                .build();
    }

    private CategoryResp toDto(CategoryEntity category) {

        return CategoryResp.builder()
                .id(category.getId())
                .name(category.getName()).build();
    }
}
