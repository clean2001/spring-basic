package com.beyond.basic.domain;

import lombok.Data;

@Data // setter나 생성자가 없으면 데이터 바인딩이 되지 않는다. 그래서 꼭 필요함!
public class MemberReqDto {
    private String name;
    private String email;
    private String password;

    // dto에서 entity로 변환
    public Member toEntity() {
        return new Member(this.name, this.email, this.password);
    }
}