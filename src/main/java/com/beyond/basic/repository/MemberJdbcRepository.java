package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

// 해당 클래스가 Repository 계층임을 표현함과 동시에 싱글톤 객체로 생성한다.
@Repository
public class MemberJdbcRepository implements MemberRepository {
    @Override
    public void save(Member member) {

    }

    @Override
    public List<Member> findAll() {
        return null;
    }

    @Override
    public Member findById(Long id) {
        return null;
    }
}
