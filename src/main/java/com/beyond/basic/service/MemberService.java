package com.beyond.basic.service;

import com.beyond.basic.controller.MemberController;
import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.repository.MemberMemoryRepository;
import com.beyond.basic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// input 값의 검증 및 실질적인 비즈니스 로직은 서비스 계층에서 수행한다.
@Service // 서비스 계층임을 표현함과 동시에 **싱글톤 객체**로 생성
public class MemberService {
    // **다형성을 구현 못했을 때의 문제점: 클래스를 바꾸었을 때, 바꾼 클래스에는 없는 메서드를 쓰고 있었을 가능성이 있음.
    private final MemberMemoryRepository memberRepository;
    //  싱글톤 객체를 주입(DI) 받는다라는  것을 의미
    @Autowired
    public MemberService(MemberMemoryRepository memoryRepository) {
        memberRepository = memoryRepository;
    }

    public void memberCreate(MemberReqDto dto) {
        if(dto.getPassword().length() < 8) {
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
        }
        // Member로 조립
        Member member = new Member();
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPassword(dto.getPassword());
        memberRepository.save(member);

        // ResDto로 조립
    }

    public MemberResDto memberDetail(Long id) {
        Member member = memberRepository.findById(id);
        MemberResDto resDto = new MemberResDto();

        resDto.setId(member.getId());
        resDto.setEmail(member.getEmail());
        resDto.setName(member.getName());

        return resDto;
    }

    public List<MemberResDto> memberList() {
        return memberRepository.findAll().stream().map(m -> {
            MemberResDto memberResDto = new MemberResDto();
            memberResDto.setName(m.getName());
            memberResDto.setEmail(m.getEmail());
            memberResDto.setId(m.getId());

            return memberResDto;
        }).collect(Collectors.toList());
    }

}

