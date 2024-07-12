package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository implements MemberRepository {
    // 엔티티 매니저란?
    // JPA의 핵심 클래스이자 객체이다. 엔티티의 생명 주기를 관리하고, 데이터베이스와의 모든 인터페이싱을 책임진다.
    // 즉, 엔티티를 대상으로 CRUD하는 기능을 제공한다
    // JPA
    @Autowired
    private EntityManager entityManager;

    public Member save(Member member) {
        // persist: 전달된 엔티티가(Member)가 EntityManager의 관리 상태가 되도록 만들어주고,
        // 트랜잭션이 커밋될 때 데이터데이스에 저장. insert
        // jpa에서는 스프링이 DB를 통제하는 것 같다는 느낌을 가져가기
        entityManager.persist(member);
        return member;
    }

    @Override
    public List<Member> findAll() {
        // jpql: jpa의 raw 쿼리 문법(객체지향)
        // jpa에서는 jpql 문법 컴파일 에러가 나오지 않으나, spring data jpa에서는 컴파일 에러 발생
        List<Member> memberList = entityManager.createQuery("select m from Member m", Member.class).getResultList();
        return memberList;
    }


    public Optional<Member> findByEmail(String email) {
        Member member = entityManager.createQuery("select m from Member m where m.email = :email",
                        Member.class)
                .setParameter("email", email)
                .getSingleResult();

        return Optional.of(member);
    }

    @Override
    public Optional<Member> findById(Long id) {

        // entity manager를 통해서 find
        // Member.class를 써주면 Member DB로 감
        // entity manager를 통해 find(리턴 타입 클래스 지정 및 매개변수로 pk 필요)
        Member member = entityManager.find(Member.class, id); // Member.class : mybatis에서 resultType과 비슷한 개념이다.
        return Optional.ofNullable(member);
    }

    // 그냥 jpa에서 pk 이외의 컬럼으로 조회할 때
    // jpql 문법으로 raw쿼리 비슷하게 직접 쿼리 작성

}
