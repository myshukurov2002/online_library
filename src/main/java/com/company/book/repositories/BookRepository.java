package com.company.book.repositories;

import com.company.book.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public interface BookRepository extends JpaRepository<BookEntity, UUID> {
    Optional<BookEntity> findByTitleAndVisibilityTrue(String title);

    Optional<BookEntity> findByIdAndVisibilityTrue(UUID id);

    List<BookEntity> findAllByVisibilityTrue();

    List<BookEntity> findAllByCategoryIdAndVisibilityTrue(UUID categoryId);
}
