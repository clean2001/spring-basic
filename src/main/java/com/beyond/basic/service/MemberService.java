package com.beyond.basic.service;

import com.beyond.basic.domain.*;
import com.beyond.basic.dto.MemberUpdateDto;
import com.beyond.basic.repository.MyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// input 값의 검증 및 실질적인 비즈니스 로직은 서비스 계층에서 수행한다.
// Transactional 어노테이션을 통해 모든 메서드에 트랜잭션을 적용하고, => 각 메서드마다 하나의 트랜잭션으로 묶겠다.
// 만약 예외가 발생시 롤백처리 자동화
// 만약 어떤 메서드에 @Transactional을 붙이지 않으면?? ==> 그 메서드에서 DB에 뭐할때마다 오토 커밋된다.
@Service // 서비스 계층임을 표현함과 동시에 **싱글톤 객체**로 생성
public class MemberService {
    // **다형성을 구현 못했을 때의 문제점: 클래스를 바꾸었을 때, 바꾼 클래스에는 없는 메서드를 쓰고 있었을 가능성이 있음.
    private final MyMemberRepository memberRepository;
    //  싱글톤 객체를 주입(DI) 받는다라는  것을 의미
    @Autowired
    public MemberService(MyMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    // 이름이 'kim'이면 예외를 터뜨린다.
    /*
    테스트 결과:
    1. @Transactional을 걸었을 때. => 롤백 돼서 저장되지 않는다.
    2. @Transactional을 걸지 않았을 때 => 롤백 되지 않아서 저장된다.
    3. @Transactional(readOnly=true)를 걸고, 예외가 터짐 => 저장되지 않는다.
    4.  @Transactional(readOnly=true)를 걸고, 예외가 터지지 않게 함 => 저장된다.
     */
    @Transactional(readOnly = true)
    public Member memberCreate(MemberReqDto dto) throws IllegalArgumentException {
        if(dto.getPassword().length() < 8) {
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
        }
        // Member로 조립
        Member member = dto.toEntity(); // 이 부분 완전 중요함!!

        // 이메일 중복 검증
        if(memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이메일 중복");
        }

        // 저장
        Member savedMember = memberRepository.save(member);

        // Transctional 롤백 처리 테스트
        if(member.getName().equals("kim")) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }

        System.out.println("member id: " + member.getId());
        savedMember = memberRepository.findById(member.getId()).orElseThrow(() -> new EntityNotFoundException("member Service line 46"));
        System.out.println(savedMember);
        
        return savedMember;
    }

    public MemberDetResDto memberDetail(Long id) throws EntityNotFoundException {

        // 예외를 터뜨리는 이유
        // 클라이언트에게 적절한 예외 메시지와 상태 코드를 주는 것이 주요 목적
        // 또한 예외를 강제 발생시킴으로써 클라이언트에게 적절한 예외 메시지를 뿌려줌
        Optional<Member> memberOpt = memberRepository.findById(id);
        Member member = memberOpt.orElseThrow(() -> new EntityNotFoundException("없는 회원입니다."));

        // 글쓴이의 글쓴 개수
        System.out.println("글쓴이의 쓴 글의 개수 " + member.getPosts().size());
        for(Post p : member.getPosts()) {
            System.out.println("글의 제목 " + p.getTitle());
        }

        return member.detFromEntity();
    }

    public List<MemberResDto> memberList() {
        List<Member> memberList = memberRepository.findAll();
        return memberList.stream().map(Member::listFromEntity).collect(Collectors.toList());
    }

    public void pwUpdate(MemberUpdateDto dto) {
        Member member = memberRepository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);
        member.updatePw(dto.getPassword());


        // 기존 객체를 조회 후 수정한 다음에 save시에는 jpa update 실행
        memberRepository.save(member); // update라는 메서드는 없다. 따라서 save를 그대로 써주면 된다.
    }

    public void deleteMember(Long memberId) {
//        memberRepository.deleteById(memberId); // 이것도 되네
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException());
        memberRepository.delete(member); // 완전 삭제

        // member.updateDelete("Y");
        //memberRepository.save(member);

    }

}

