package com.company.book.servies;

import com.company.base.ApiResponse;
import com.company.book.dtos.BookCr;
import com.company.book.dtos.BookResp;
import com.company.book.dtos.BookUpd;

import java.util.List;
import java.util.UUID;

public interface BookService {
    ApiResponse<BookResp> create(BookCr dto);

    ApiResponse<BookResp> update(UUID id, BookUpd dto);

    ApiResponse<BookResp> delete(UUID id);
    ApiResponse<BookResp> getById(UUID id);

    ApiResponse<List<BookResp>> getList();

    ApiResponse<List<BookResp>> getAllByCategoryId(UUID categoryId);
}
