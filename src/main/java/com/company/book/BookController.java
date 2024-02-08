package com.company.book;

import com.company.base.ApiResponse;
import com.company.book.dtos.BookCr;
import com.company.book.dtos.BookResp;
import com.company.book.dtos.BookUpd;
import com.company.book.servies.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<BookResp>> create(@RequestBody BookCr dto) {
        return ResponseEntity.ok(bookService.create(dto));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<BookResp>> update(@PathVariable UUID id,
                                                        @Valid @RequestBody BookUpd dto) {
        return ResponseEntity.ok(bookService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<BookResp>> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(bookService.delete(id));
    }

    @GetMapping("/open/get-by-id/{id}")
    public ResponseEntity<ApiResponse<BookResp>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @GetMapping("/open/get-all")
    public ResponseEntity<ApiResponse<List<BookResp>>> getList() {
        return ResponseEntity.ok(bookService.getList());
    }

    @GetMapping("/open/get-all-by-category-id/{categoryId}")
    public ResponseEntity<ApiResponse<List<BookResp>>> getAllByCategoryId(
            @PathVariable UUID categoryId
    ) {
        return ResponseEntity.ok(bookService.getAllByCategoryId(categoryId));
    }
}