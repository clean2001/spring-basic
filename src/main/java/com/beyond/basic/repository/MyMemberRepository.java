package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyMemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email); // 런타임에 구현체에 만들어짐(리플렉션 기술로){
}
