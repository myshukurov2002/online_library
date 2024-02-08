package com.company.book.servies;

import com.company.attach.AttachService;
import com.company.base.ApiResponse;
import com.company.book.dtos.BookCr;
import com.company.book.dtos.BookResp;
import com.company.book.dtos.BookUpd;
import com.company.book.entities.BookEntity;
import com.company.book.repositories.BookRepository;
import com.company.category.dtos.CategoryResp;
import com.company.category.services.CategoryService;
import com.company.config.i18n.MessageService;
import com.company.expections.exp.DuplicateItemException;
import com.company.expections.exp.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private final AttachService attachService;
    private final MessageService messageService;

    @Override
    public ApiResponse<BookResp> create(BookCr dto) {

        bookRepository
                .findByTitleAndVisibilityTrue(dto.title())
                .ifPresent(b -> {
                    throw new DuplicateItemException();
                });

        BookEntity book = toEntity(dto);

        bookRepository.save(book);

        log.info("book created id: " + book.getId());

        return new ApiResponse<>(true, toDto(book));
    }

    @Override
    public ApiResponse<BookResp> update(UUID id, BookUpd dto) {

        BookEntity book = get(id);

        book.setTitle(dto.title());
        book.setAuthor(dto.author());
        book.setDescription(dto.description());

        log.info("book updated id: " + book.getId());

        return new ApiResponse<>(true, toDto(book));
    }

    @Override
    public ApiResponse<BookResp> delete(UUID id) {

        BookEntity book = get(id);

        book.setVisibility(false);
        bookRepository.save(book);

        log.info("book deleted id: " + book.getId());

        return new ApiResponse<>(true, messageService.getMessage("success.deleted"));
    }

    @Override
    public ApiResponse<BookResp> getById(UUID id) {

        return new ApiResponse<>(true, toDto(get(id)));
    }

    @Override
    public ApiResponse<List<BookResp>> getList() {

        List<BookResp> list = bookRepository
                .findAllByVisibilityTrue()
                .stream()
                .map(this::toDto)
                .toList();

        return new ApiResponse<>(true, list);
    }

    @Override
    public ApiResponse<List<BookResp>> getAllByCategoryId(UUID categoryId) {

        List<BookResp> list = bookRepository
                .findAllByCategoryIdAndVisibilityTrue(categoryId)
                .stream()
                .map(this::toDto)
                .toList();

        return new ApiResponse<>(true, list);
    }

    private BookEntity get(UUID id) {

        return bookRepository
                .findByIdAndVisibilityTrue(id)
                .orElseThrow(ItemNotFoundException::new);
    }

    private BookEntity toEntity(BookCr dto) {

        return BookEntity.builder()
                .title(dto.title())
                .author(dto.author())
                .description(dto.description())
                .categoryId(dto.categoryId())
                .pdfId(dto.pdfId())
                .photoId(dto.photoId()).build();
    }

    private BookResp toDto(BookEntity book) {

        return BookResp.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .description(book.getDescription())
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
