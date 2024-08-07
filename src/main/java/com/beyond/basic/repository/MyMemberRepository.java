package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MyMemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email); // 런타임에 구현체에 만들어짐(리플렉션 기술로)

    List<Member> findByName(String name); // 이거 Optional로 받으면 어떻게 되는지 궁금해지네

    @Query("select m from Member m") // Member 첫글자 꼭 대문자로 써줘야함
    List<Member> myFindAll();
}
