package com.beyond.basic.controller;

import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberDetResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.service.MemberService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller // 싱글톤임. @Component라고 붙어있는 애들은 전부다 싱글톤이다.
public class MemberController {
    // 의존성 주입(DI) 방법 1. 생성자 주입 방식 ==> 가장 많이 사용하는 방식
    // 장점: 1) final을 통해 상수로 사용 가능 / 2) 다형성 구현 가능 / 3) 순환 참조 방지
    // 순환 참조란?? => 순환해서 참조하고 있다는 의미이다.
    // 컴파
    // Controller Service =>
    // Controller안에 Servief를
//    private final MemberService memberService;
//
//    @Autowired
//    public MemberController(MemberService memberService) {
//        this.memberService = memberService;
//    }

    //== 의존성 주입 방법 2. 필드 주입 방식 (@Autowired만 사용) ==//
    // 단점 1. 초기화가 안되기 때문에 final 키워드를 붙일 수 없다.
    // 단점 2. 다형성 구현 불가능


    // 의존 주입 방법 3. 어노테이션(RequiredArgs)를 이용하는 방식
    // @RequiredArgsConstructor: @NonNull 어노테이션, final 키워드가 붙어있는 필드를 자동으로 의존성 주입
    private final MemberService memberService;

//    @NonNull
//    private MemberService memberService;

    @GetMapping
    public String home() {
        return "member/home";
    }

    //== 회원 목록 조회 ==//
    @GetMapping("/member/list")
    public String memberList(Model model) {
        List<MemberResDto> memberList = memberService.memberList();
        model.addAttribute("memberList", memberList);
        return "member/member-list";
    }

    //== 회원 상세 조회 ==//
    // url(uri) : member/1, member/2
    // 화면명: member-detail
    // int 또는 long 받을 경우 스프링에서 형변환 해준다. (String -> Long)
    @GetMapping("/member/detail/{id}")
    public String memberDetail(@PathVariable Long id, Model model) {
        MemberDetResDto memberDetResDto = memberService.memberDetail(id);
        model.addAttribute("memberResDto", memberDetResDto);
        return "member/member-detail";
    }

    //== 회원 가입 화면 ==//
    // url: member/create
    @GetMapping("/member/create")
    public String createMemberForm() {
        return "member/member-create";
    }

    // url: member/create
    // name, email, password
    // Jdbc <db 접근 기술>
    // jpa: 쿼리가 있을 때도, 없을 때도 있음
    // Spring Data Jpa: 쿼리 없음 (쿼리 X == ORM)
    @PostMapping("/member/create")
    public String memberCreatePost(MemberReqDto dto, Model model) {

        System.out.println(dto);

        try {
            memberService.memberCreate(dto); // 리포지토리에 저장하기
        } catch(Exception e) {
            model.addAttribute("error", e.getMessage());
            return "member/member-error";
        }

        // member/list로 리다이렉트
        // 화면 리턴이 아닌 url 재호출(리다이렉트)
        return "redirect:/member/list";
    }



}
