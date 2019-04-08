package com.tencent.tsf.container.dto;

import lombok.Data;

@Data
public class NamespaceDTO {
    private String id;
    private String name;
    private String status;
    private String createdAt;
    private String updatedAt;
}
