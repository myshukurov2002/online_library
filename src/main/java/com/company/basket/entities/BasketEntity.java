package com.company.basket.entities;

import com.company.base.BaseEntity;
import com.company.book.entities.BookEntity;
import com.company.user.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "basket")
public class BasketEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "book_id",
            insertable = false,
            updatable = false
    )
    private BookEntity book;

    @Column(
            name = "book_id"
    )
    private UUID bookId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            insertable = false,
            updatable = false
    )
    private UserEntity user;

    @Column(
            name = "user_id"
    )
    private UUID userId;

    @Column
    private Boolean cartStatus = false;
}
