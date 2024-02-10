package com.company.book.mapper;

import com.company.attach.AttachService;
import com.company.book.dtos.BookResp;
import com.company.book.entities.BookEntity;
import com.company.category.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
@RequiredArgsConstructor
public class BookDtoMapper implements Function<BookEntity, BookResp> {

    private final CategoryService categoryService;
    private final AttachService attachService;
    @Override
    public BookResp apply(BookEntity book) {
        return BookResp.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .description(book.getDescription())
                .price(book.getPrice())
                .category(
                        categoryService
                                .getById(book.getCategoryId())
                                .data()
                )
                .photo(
                        attachService
                                .toDTO(attachService.get(book.getPhotoId()))
                )
                .pdf(
                        attachService
                                .toDTO(attachService.get(book.getPdfId()))
                ).build();
    }
}
