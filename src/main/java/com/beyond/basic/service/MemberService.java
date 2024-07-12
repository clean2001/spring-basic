package com.beyond.basic.service;

import com.beyond.basic.controller.MemberController;
import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberDetResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.repository.*;
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
@Transactional
@Service // 서비스 계층임을 표현함과 동시에 **싱글톤 객체**로 생성
public class MemberService {
    // **다형성을 구현 못했을 때의 문제점: 클래스를 바꾸었을 때, 바꾼 클래스에는 없는 메서드를 쓰고 있었을 가능성이 있음.
    private final MemberRepository memberRepository;
    //  싱글톤 객체를 주입(DI) 받는다라는  것을 의미
    @Autowired
    public MemberService(MemberSpringDataJpaRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public Member memberCreate(MemberReqDto dto) {
        if(dto.getPassword().length() < 8) {
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
        }
        // Member로 조립
        Member member = new Member();
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPassword(dto.getPassword());
        memberRepository.save(member);

        System.out.println("member id: " + member.getId());
        Member savedMember = memberRepository.findById(member.getId()).orElseThrow(() -> new EntityNotFoundException("member Service line 46"));
        System.out.println(savedMember);
        
        return savedMember;
    }

    public MemberDetResDto memberDetail(Long id) {

        // 예외를 터뜨리는 이유
        // 클라이언트에게 적절한 예외 메시지와 상태 코드를 주는 것이 주요 목적
        // 또한 예외를 강제 발생시킴으로써 클라이언트에게 적절한 예외 메시지를 뿌려줌
        Optional<Member> memberOpt = memberRepository.findById(id);
        Member member = memberOpt.orElseThrow(() -> new EntityNotFoundException("없는 회원입니다."));
        MemberDetResDto resDto = new MemberDetResDto();

        resDto.setId(member.getId());
        resDto.setEmail(member.getEmail());
        resDto.setName(member.getName());
        resDto.setPassword(member.getPassword());

        return resDto;
    }

    public List<MemberResDto> memberList() {
        return memberRepository.findAll().stream().map(m -> {
            if(m == null) return null;
            MemberResDto memberResDto = new MemberResDto();
            memberResDto.setId(m.getId());
            memberResDto.setName(m.getName());
            memberResDto.setEmail(m.getEmail());
//            memberDetResDto.setPassword(m.getPassword());

            return memberResDto;
        }).collect(Collectors.toList());
    }

}

