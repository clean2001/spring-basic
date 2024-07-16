package com.beyond.basic.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

// @Entity 어노테이션을 통해 엔티티 매니저에게 객체 관리를 위힘
// 해당 클래스 명으로 테이블 자동 생성하고 각종 설정 정보를 위임한다.
@Data // FIXME: 삭제 필요
@Entity // JPA에게 entity manager가 관리해야하는 요소라는 것을 알려주는 것이다.
@NoArgsConstructor
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

    @OneToMany(mappedBy = "member") // 여기서 mappedBy에 들어가는 것은 매핑이되어 있는 **변수명**이다
    private List<Post> posts; // 변수명 아무거나 쓰면 된다.

    @CreationTimestamp // DB에는 current_timestamp가 생성되지 않음
    private LocalDateTime createdTime; // 카멜 케이스 사용시 DB에는 스네이크 케이스로 생성된다.

    @UpdateTimestamp
    private LocalDateTime updateTime; // 값을 수정할 때마다 값이 계속 갱신되어야함


    // 직접 만들어준 생성자
    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }


    //== fromEntity ==//
    public MemberDetResDto detFromEntity() {
//        return new MemberDetResDto(this);
        return new MemberDetResDto(this.id, this.name, this.email, this.password, createTimeStr(this.createdTime));
    }

    public MemberResDto listFromEntity() {
//        return new MemberResDto(this);
        return new MemberResDto(this.id, this.name, this.email);
    }

    private String createTimeStr(LocalDateTime createdTime) {
        return createdTime.getYear() + "년 " + createdTime.getMonthValue() + "월 " + createdTime.getDayOfMonth() + "일";
    }

    // password 상단에 @Setter를 통해 특정 변수만 setter 사용이 가능하나,
    // 일반적으로 의도를 명확하게 한 메서드를 별도로 만들어 사용하는 것을 권장.
    public void updatePw(String password) {
        this.password = password;
    }
}
