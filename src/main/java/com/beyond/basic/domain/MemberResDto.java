package com.beyond.basic.domain;

import lombok.Data;

@Data
public class MemberResDto {
    private Long id;
    private String name;
    private String email;

    public MemberResDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
    }
}
