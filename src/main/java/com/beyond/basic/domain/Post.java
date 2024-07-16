package com.beyond.basic.domain;

import com.beyond.basic.dto.PostResDto;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // 1:1의 경우 OneToOne을 설정하고, unique = true로 설정한다. => 나중에 주문시스템 가면 OneToOne 관계를 볼 수도 있다.

    @ManyToOne
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE) // cascade를 양쪽에 걸어줘야하는건가??
    private Member member;

    // post.getMember() => 이렇게 하는 순간 member를 조회해줌 (entity Manager가 해준다.) JPA: java persistence api
    // JPA의 영속성 컨텍스틍 의해 Member 객체가 관리된다.
    public PostResDto fromEntity() {
        return new PostResDto(this.id, this.title);
    }
}
