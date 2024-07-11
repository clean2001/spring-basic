package com.beyond.basic.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Member {
    private Long id;
    private String name;
    private String email;
    private String password;
}
