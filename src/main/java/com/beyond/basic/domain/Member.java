package com.beyond.basic.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity // JPA에게 entity manager가 관리해야하는 요소라는 것을 알려주는 것이다.
// @Entity 어노테이션을 통해 엔티티 매니저에게 객체 관리를 위힘
// 해당 클래스 명으로 테이블 자동 생성하고 각종 설정 정보를 위임한다.
public class Member {
    @Id // pk 설정 => 이 설정을 반드시 해줘야한다!!!!!!!
    @GeneratedValue(strategy = GenerationType.IDENTITY) // identity: auto_increment 설정 / auto: jpa 자동으로 적절한 전략을 선택하도록 맡기는 것
    private Long id;
    // String은 varchar(255)로 변환된다. name 변수명이 name 컬럼명으로 변환
    private String name;

    @Column(nullable = false, unique=true, length = 50) // not null 제약조건 + unique 제약조건
    private String email;
//    @Column(name="pw") // 이렇게 컬럼명과 다르게 할 수 있으나, 컬럼명과 변수명을 일치시키는 것이 혼선을 줄일 수 있다.
    // 만약 변수명을 passWord 이렇게 붙이면?? ==> 컬럼명은 pass_word 이렇게 들어감
    private String password;
}
