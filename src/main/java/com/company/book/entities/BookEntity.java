package com.company.book.entities;


import com.company.attach.entity.AttachEntity;
import com.company.base.BaseEntity;
import com.company.category.entities.CategoryEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class BookEntity extends BaseEntity {

    @Column(
            name = "title",
            nullable = false,
            length = 100)

    private String title;

    @Column(
            name = "author",
            nullable = false,
            length = 100
    )
    private String author;

    @Column(
            columnDefinition = "text"
    )
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;

    @Column(name = "category_id")
    private UUID categoryId;

    @OneToOne
    @JoinColumn(
            name = "photo_id",
            updatable = false,
            insertable = false
    )
    private AttachEntity photo;

    @Column(
            name = "photo_id"
    )
    private UUID photoId;

    @OneToOne
    @JoinColumn(
            name = "pdf_id",
            updatable = false,
            insertable = false
    )
    private AttachEntity pdf;

    @Column(
            name = "pdf_id"
    )
    private UUID pdfId;

    @Override
    public void setVisibility(Boolean visibility) {
        super.setVisibility(visibility);

        if (!visibility) {

            photo.setVisibility(false);
            pdf.setVisibility(false);
        }
    }
}
