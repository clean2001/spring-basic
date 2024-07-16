package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;

import java.util.List;
import java.util.Optional;

// 다형성을 위해 만듦 =>
// interface에는 어떤 메소드를 쓸지 스펙만 정의해주면 된다.
public interface MemberRepository {
    // save하고 Member 리턴해도 됨. => 저장하고 나서 Member를 되돌려주는 이유: 방금 준 멤버 데이터가 잘 저장됐다고 알려주는 것이다.
    Member save(Member member);

    // 모든 멤버 조회
    List<Member> findAll();

    // 아이디로 멤버 조회
    Optional<Member> findById(Long id);

}
