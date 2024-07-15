package com.beyond.basic.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor // Object Mapper에서 기본 생성자와 Getter가 필요함
@Data
public class MemberDetResDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String createdTime;

    //== 생성자 ==//
    public MemberDetResDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.createdTime = createTimeStr(member.getCreatedTime());
    }

    private String createTimeStr(LocalDateTime createdTime) {
        return createdTime.getYear() + "년 " + createdTime.getMonthValue() + "월 " + createdTime.getDayOfMonth() + "일";
    }
}
