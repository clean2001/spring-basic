package com.beyond.basic.controller;

import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberDetResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.dto.MemberUpdateDto;
import com.beyond.basic.dto.TestRequest;
import com.beyond.basic.dto.TestResponse;
import com.beyond.basic.response.CustomResponse;
import com.beyond.basic.response.ErrorResponse;
import com.beyond.basic.response.SuccessResponse;
import com.beyond.basic.service.MemberService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Api(tags = "회원 관리 서비스")
@RequiredArgsConstructor
@RequestMapping("/rest")
// RestController의 경우 모든 메서드 상단에 @ResponseBody가 붙는 효과 발생
// RestController는 @ResponseBody만 붙여주는거임 => @RequestBody는 다 따로 붙여주어야 한다.
@RestController
public class MemberRestController {

    private final MemberService memberService;

    @GetMapping("/member/text")
    public String memberText() {
        return "ok";
    }


    //== 회원 목록 조회 ==//
    @GetMapping("/member/list")
    public ResponseEntity<CustomResponse> memberList() {
        List<MemberResDto> memberList = memberService.memberList();
        SuccessResponse successResponse = new SuccessResponse(OK, "멤버 리스트", memberList);
        return new ResponseEntity<>(successResponse, OK);
    }

    //== 회원 상세 조회 ==//
    @GetMapping("/member/detail/{id}")
    public ResponseEntity<CustomResponse> memberDetail(@PathVariable Long id) {
        MemberDetResDto memberResDto;

        try {
            memberResDto = memberService.memberDetail(id);
            return new ResponseEntity<>(new SuccessResponse(OK, "멤버 찾음", memberResDto), OK);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(NOT_FOUND.value(), e.getMessage()), NOT_FOUND);
        }
    }

    @PostMapping("/member/create")
    public ResponseEntity<CustomResponse> memberCreatePost(@RequestBody MemberReqDto dto) { // 지금 이거는 JSON 구조가 아니기 때문에 문제가 없음. 만약 json이면 NoArgs가 필요했을 것으로 보임
        Member member;
        try {
            member = memberService.memberCreate(dto); // 리포지토리에 저장하기
            return new ResponseEntity<>(new SuccessResponse(CREATED, "멤버 생성", member), CREATED);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorResponse(BAD_REQUEST.value(), e.getMessage()), BAD_REQUEST);
        }

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
