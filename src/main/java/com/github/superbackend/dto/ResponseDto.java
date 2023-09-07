package com.github.superbackend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class ResponseDto {
    private String status;
    private String message;
}
