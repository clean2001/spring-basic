package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

// MemberMemoryRepository는 싱글톤 객체이므로 한번만 생성된다!!
@Repository
public class MemberMemoryRepository implements MemberRepository {
    private final List<Member> memberList;

    MemberMemoryRepository() {
        this.memberList = new ArrayList<>();
    }

    @Override
    public void save(Member member) {
        // DB에 저장하는 로직
        memberList.add(member);
    }

    @Override
    public List<Member> findAll() {
        return memberList;
    }

    @Override
    public Member findById(Long id) {
        return null;
    }
}
