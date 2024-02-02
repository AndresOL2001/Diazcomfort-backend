package com.diazcomfort.backend.DTOs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PMImageDTO {
    public String imageId;

    public String url;
}
