package com.beyond.basic.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDetResDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String createdTime;
}
