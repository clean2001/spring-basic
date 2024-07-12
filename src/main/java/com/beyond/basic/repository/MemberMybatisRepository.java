package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

// 해당 레포지토리를 mybatis 기술을 쓰겠다는 표현!!

@Mapper
@Repository
public interface MemberMybatisRepository extends MemberRepository {
    // 인터페이스와 인터페이스 간의 상속을 하면, 밑에 있는 메서드들을 쓸 필요가 없다.
//    List<Member> findAll();
//    Member save(Member member);
//    Member findById(Long id);

}
