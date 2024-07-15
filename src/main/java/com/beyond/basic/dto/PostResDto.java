package com.beyond.basic.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter

public class PostResDto {
    private Long id;
    private String title;
}
