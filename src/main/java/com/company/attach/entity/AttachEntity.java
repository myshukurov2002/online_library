package com.company.attach.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "attach")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AttachEntity {

    @Id
    private UUID id;

    @Column
    private String originalName;
    @Column
    private String path;
    @Column
    private Long size;
    @Column
    private String extension;
    @Column
    private Boolean visibility;
}
