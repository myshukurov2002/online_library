package com.company.category.entities;

import com.company.base.BaseEntity;
import com.company.book.entities.BookEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "category")
public class CategoryEntity extends BaseEntity {

    @Column
    private String name;

    @OneToMany(
            mappedBy = "category"
    )
    List<BookEntity> books;

    @Override
    public void setVisibility(Boolean visibility) {

        super.setVisibility(visibility);

        if (!visibility) {

            for (BookEntity book : books) {
                book.setVisibility(false);
            }

        }
    }
}
