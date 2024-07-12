package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// spring data jpa repository가 되기 위해서는 JpaRepository를 상속해야하고, 상속시에 Entity명과 Pk의 타입을 명시해야한다.
// 같이 상속하면 에러.
// 이유: JpaRepository에는 모든 메소드가 이미 선언되어있다.
// 리턴 타입도 다 정의되어 있음.

// MemberRepository가 되기 위해서는 JpaRepository를 상속해야하고, 상속시에 Entity명과 엔티티의 pk를 적어줘야한다.
// MemberRepository는 JpaRepository를 상속함으로써 JpaRepsitory의 주요 기능을 상속
// JpaRepository가 인터페이스임에도 해당 기능을 상속해서 사용할 수 있는 이유는 hibernate 구현체가 미리 구현돼 있기 때문이다.
// 런타임 시점에 사용자가 인터페이스에 정의한 메서드를 프록시(대리인) 객체가 리플렉션 기술을 통해 메서드를 구현한다.

@Repository
public interface MemberSpringDataJpaRepository extends MemberRepository, JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email); // 런타임에 구현체에 만들어짐(리플렉션 기술로)

}
