package com.tencent.tsf.container.dto;

import lombok.Data;

@Data
public class ClusterDTO {
    private String id;
    private String name;
    private String status;
    private String cidr;
    private Integer totalNodeNum;
    private Integer runningNodeNum;
    private Integer runningPodNum;
    private String createdAt;
    private String updatedAt;
}
