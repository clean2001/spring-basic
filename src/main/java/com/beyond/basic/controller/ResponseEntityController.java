package com.beyond.basic.controller;

import com.beyond.basic.domain.Member;
import com.beyond.basic.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/response/entity")
@RestController
public class ResponseEntityController {
    // @ResponseStatus 어노테이션 방식
    @GetMapping("/annotation1")
    public String annotation1() {
        return "ok";
    }

    @GetMapping("/annotation2")
    @ResponseStatus(CREATED)
    public Member annotation2() {
        // (가정) 객체 생성 후 DB 저장 성공
        Member member = new Member("hong", "hong@naver.com", "1234");
        return member;
    }

    //== response 헤더에 상태값 끼워넣기 ==//

    // case 2. 메서드 체이닝 방식: ResponseEntity의 클래스 메서드 사용
    @GetMapping("/chaining1")
    public ResponseEntity<Member> chaining1() {
        Member member = new Member("hong", "hong@naver.com", "1234");
        return ResponseEntity.ok(member);
    }

    // case 2. 메서드 체이닝 2:
    @GetMapping("/chaining2")
    public ResponseEntity<Member> chaining2() {
        Member member = new Member("hong", "hong@naver.com", "1234");
        return ResponseEntity.status(CREATED).body(member);
    }

    // case 2. 메서드 체이닝 3: notFound
    @GetMapping("/chaining3")
    public ResponseEntity<Member> chaining3() {
        return ResponseEntity.notFound().build();
    }

    //== response 헤더에 상태값 끼워 넣기 끝 ==//

    // case 3. ResponseEntity를 직접 커스텀하여 생성하는 방식
    @GetMapping("/custom1")
    public ResponseEntity<Member> custom1() {
        Member member = new Member("hong", "hong@naver.com", "1234");
        return new ResponseEntity<>(member, CREATED);
    }


    @GetMapping("/custom2")
    public ResponseEntity<SuccessResponse<Member>> custom2() {
        Member member = new Member("hong", "hong@naver.com", "1234");
        // 헤더에 넣는건 필수이다. 바디에 넣는건 Optional이다.
        return new ResponseEntity<>(new SuccessResponse<>(CREATED, CREATED.getReasonPhrase(), member), OK);
    }
}
