package com.beyond.basic.controller;

import com.beyond.basic.domain.MemberDetResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.dto.MemberUpdateDto;
import com.beyond.basic.dto.TestRequest;
import com.beyond.basic.dto.TestResponse;
import com.beyond.basic.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/rest")
// RestController의 경우 모든 메서드 상단에 @ResponseBody가 붙는 효과 발생
// RestController는 @ResponseBody만 붙여주는거임 => @RequestBody는 다 따로 붙여주어야 한다.
@RestController
public class MemberRestController {

    private final MemberService memberService;

    //== 회원 목록 조회 ==//
    @GetMapping("/member/list")
    public List<MemberResDto> memberList() {
        return memberService.memberList();
    }

    //== 회원 상세 조회 ==//
    @GetMapping("/member/detail/{id}")
    public MemberDetResDto memberDetail(@PathVariable Long id) {
        return memberService.memberDetail(id);
    }

    @PostMapping("/member/create")
    public String memberCreatePost(@RequestBody MemberReqDto dto) { // 지금 이거는 JSON 구조가 아니기 때문에 문제가 없음. 만약 json이면 NoArgs가 필요했을 것으로 보임
        try {
            memberService.memberCreate(dto); // 리포지토리에 저장하기
        } catch(Exception e) {
            return "error: 저장 실패";
        }
        return "ok";
    }

    // 테스트 결과 정리
    // Object Mapper가 역직렬화를 할 때(즉 요청 객체에서)는 NoArgsConstructor가 반드시 필요하다. getter도 필요없음
    // Object Mapper가 직렬화를 할 때 NoArgsConstructor가 필요없다. 대신 getter가 반드시 필요함!!
    @PostMapping("/test")
    public TestResponse testApi(@RequestBody TestRequest testRequest) {
        return new TestResponse("결과값~~~");
    }

    // Put으로 하고 @RequestParam하면 => body에다가 키벨류 형식으로 받을 수 있음
    @PatchMapping("/member/pw/update")
    public void memberList(@RequestBody MemberUpdateDto dto) {
        memberService.pwUpdate(dto);
    }

    // 만약 delYn으로 상태 관리를 할 것임. 그럼 실제론 update를 칠건데, @DeleteMapping이 맞을까??
    // 선택의 문제이긴하지만 맞다. 왜냐면 논리적으로 지우는 작업이긴 하니까 (
    @DeleteMapping("/member/delete/{id}")
    public void deleteMember(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
    }



}
