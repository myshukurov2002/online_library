package com.company.attach.dtos;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachResp {

    private UUID id;
    private String originalName;
    private String path;
    private String url;
    private Long size;

    private String extension;

    private LocalDateTime createdData;
}
