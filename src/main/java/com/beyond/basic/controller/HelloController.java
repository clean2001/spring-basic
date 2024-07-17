package com.beyond.basic.controller;

import com.beyond.basic.domain.Hello;
import com.beyond.basic.domain.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 해당 클래스가 컨트롤러(사용자의 요청을 처리하고 응답하는 편의 기능)임을 명시한다.
@RequestMapping("/hello") // 클래스 차원에 url 매핑시에 RequestMapping을 사용한다.
@Controller
//@RestController // Controller + 각 메서드마다 @ResponseBody가 붙어있는 것
public class HelloController {
    //    @ResponseBody // ResponseBody를 사용하면 화면이 아닌 데이터를 return 해준다! 만약 ResponseBody가 없으면 스프링은 templates 폴더 밑에 helloworld.html 화면을 찾아 리턴한다.
    // 만약 여기서 responsebody가 없고  return 타입이 스트링이면 스프링은
    @GetMapping // Get 요청을 처리. url 패턴을 명시
    public String helloWorld(HttpServletRequest request) {
        System.out.println(request.getSession());
        System.out.println(request.getHeader("Cookie"));
        return "he lloworld";
    }



    // data를 리턴하되, json 형식으로 리턴
    // Method 명은 helloJson, url 패턴은 /hello/json
    // responsebody를 사용하면서 객체를 return시 자동으로 직렬화된다.
//    @GetMapping("/json")
    @RequestMapping(value = "/json", method = RequestMethod.GET) // 메서드 차원에서도 RequestMapping 사용 가능
    @ResponseBody
    public Hello helloJson() throws JsonProcessingException {
        return new Hello("sejeong", "clean@gmail.com", "1234");
    }

    @GetMapping("/json2")
    @ResponseBody
    public Map<String, String> helloJson2() {
        Map<String, String> map = new HashMap<>();
        map.put("hello", "hi");

        return map;
    }

    // get 요청 중에 특정 데이터를 요청

    // case1. 사용자가 서버에게 화면 요청
    // case2. @ResponseBody가 붙을 경우 단순 String 데이터 return;
    // case3. 사용자가 json 데이터를 요청
    // case4. 사용자가 json 데이터를 요청하되, 파라미터 형식으로 특정 객체 요청
    // case5. parameter 형식으로 요청 하되, 서버에서 데이터바인딩 하는 형식
    // case6.
    @GetMapping("/param1")
    @ResponseBody
    public Hello param1(@RequestParam(value = "name") String name) {
        // parameter 형식: ?name=honggildong&email=honggildong
        return new Hello(name, name + "@naver.com", "1234");
    }


    @GetMapping("/param2")
    @ResponseBody
    public Hello aram2(@RequestParam(value = "name") String name, @RequestParam(value = "email") String email) {
        // parameter 형식: ?name=honggildong&email=honggildong
        return new Hello(name, email, "1234");
    }

    // parameter가 많은 경우 객체로 대체가 가능하다. 객체에 각 변수에 맞게 알아서 바인딩된다!
    // 데이터 바인딩에서는, 값을 입력하지 않으면 null로 자동으로 들어간다.
    // 데이터 바인딩의 조건 2가지: 1. 기본 생성자가 반드시 있어야한다. 2. setter가 열려있어야함 (아니면 AllArgsConstructor)
    // Controller는 setter를 쓸 수 있는 계층이다.
    // case5. parameter 형식으로 요청 하되, 서버에서 데이터바인딩 하는 형식
    @GetMapping("/param3")
    @ResponseBody
    public Hello param3(Hello hello) {
        // parameter 형식: ?name=honggildong&email=honggildong
        System.out.println(hello);
        return hello;
    }

    // case6. 서버에서 화면에 데이터를 넣어 사용자에게 return(model 객체 사용)
    @GetMapping("/model-param")
    public String modelParam(@RequestParam(value = "name") String inputName, Model model) {
        // model 객체에 Name이라는 키값에 value를 세팅하면 해당 key:value는 화면으로 전달된다.
        model.addAttribute("name", inputName);
        return "helloworld";
    }

    // 1. 파라미터 방식: ?name=이름
    // 2. path variable 방식: localhost:8080/hello/이름 => 좀더 RESTful한 방식이다.== REST api에 적합하다.

    // case7. path variable 방식을 통해 사용자로부터 값은 받아 화면 리턴
    // localhost:8080/hello/model-path/honggildong
    // pathvariable 방식은 url을 통해 자원의 **구조를 명확하게 표현**함으로, 좀 더 restful한 형식이다.
    // restful이란 http 통신의 큰 원칙 중 하나이다.
    // pathvariable 방식
    @GetMapping("/model-path/{name}")
    public String modelPath(@PathVariable(value = "name") String inputName, Model model) {
        model.addAttribute("name", inputName);
        return "helloworld";
    }

    // Post 요청(사용자 입장에서 서버에 데이터를 주는 상황)
    // case1.
    // post 요청 방식
    // 1. html로 form 태그를 사용하여 post 요청 -> (url 인코딩 - text만, multipart)
    // 2. js 이용 -> (1) json (2) Form 데이터
    // url 인코딩 형식: key1=value1&key2=value2&key3=value3 => key는 form의 input 태그의 name 속성과 매핑된다.
    @GetMapping("/form-view")
    public String formView() {
        return "form-view";
    }


    // @RequestParam 안쓰고 객체로 받아도 된다!
    @PostMapping("/form-post1") // getmapping과 같은 url 패턴 사용도 가능하다!
    @ResponseBody
    public String formPost1(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        System.out.println("name: " + name + ", email: " + email + ", password: " + password);

        return "ok";
    }


    // @RequestParam 안쓰고 객체로 받아도 된다!
    @PostMapping("/form-post2") // getmapping과 같은 url 패턴 사용도 가능하다!
    @ResponseBody
    // @ModelAttribute는 생략 가능 => 명시해주는 것이 더 좋지만, 강사님은 안붙이신다고 하심.
    // @ModelAttribute: 데이터 바인딩. @ModelAttribute는 붙여도 되고 안붙여도 된다!!
    public Hello formPost2(@ModelAttribute Hello hello) { // x-www 아니면 multipart => key-value 형식
        System.out.println(hello);

        return hello;
    }

    // case2. multipart/form-data 방식 (text와 파일 전송)
    // form-post3
    // url 명: form-file-post, 메서드명: formFilePost, 화면명: form-file-view
    // form 태그: name, email, password, file
    @GetMapping("/form-file-post")
    public String formPost3() {
        return "form-file-post";
    }

    @PostMapping("/form-file-post")
    @ResponseBody
    // 아니면 Multipart를 Hello에다가 추가 시켜도 된다.
    // Multipart file 잘 기억하기!!!!
    // 여기 다 Hello 객체 앞에 @ModelAttribute 붙여도 된다!
    public String formFileHandle(Hello hello, @RequestParam(value = "photo") MultipartFile photo) {
        System.out.println(hello);
        System.out.println(photo.getOriginalFilename());
        return "ok";
    }

    //== case 3. js를 활용한 form 데이터 전송 ==//
    @GetMapping("/axios-form-view") // 나중에 CSR로 넘어가면 form을 띄워주는 getmapping은 필요가 없다.
    public String jsonFormView() {
        return "axios-form-view";
    }

    // axios를 통해 넘어오는 형식이 key1=value1&key2=value2&key3=value3
    @PostMapping("/axios-form-view")
    @ResponseBody
    public String axiosFormPost(Hello hello) {
        // name, email, password를 전송 => js에서 FormData() 이용
        System.out.println(hello);
        return "ok";
    }
    //== case 3 end ==//


    //== case 4. form를 활용한 form 데이터 전송(+file) ==//
    @GetMapping("axios-form-file-view")
    public String axiosFormFileView() {
        return "axios-form-file-view";
    }


    @PostMapping("axios-form-file-view")
    @ResponseBody
    public String axiosFormFileViewPost(Hello hello, @RequestParam(value = "photo") MultipartFile file) {
        System.out.println(hello);
        System.out.println(file.getOriginalFilename());
        return "ok";
    }
    //== case 4 end ==//

    //== case 5. js를 활용한 json 데이터 전송 ==//
    // url 패턴: axios-json-view, 화면명: axios-json-view, get요청 메서드명: 동일
    // post 요청 메서드: axiosJsonPost
    @GetMapping("axios-json-view")
    public String axiosJsonView() {
        return "axios-json-view";
    }

    // 이전에 바인딩하던 방법: 파라미터 방법
    // json으로 전송한 데이터를 받을 때에는 @RequestBody 어노테이션 사용

    // 서버와 화면을 같이(템플릿 엔진): form 태그
    @ResponseBody
    @PostMapping("axios-json-view")
    public String axiosJsonPost(@RequestBody Hello hello) { // @RequestBody를 써서 json 데이터를 받아줄 수 있다
        System.out.println(hello);
        return "ok";
    }
    //== case 5 end ==//

    //== case 6. js를 활용한 json 데이터 전송 (+ file) ==//
    // url: axios-json-file-view
    @GetMapping("/axios-json-file-view")
    public String axiosJsonFileView() {
        return "axios-json-file-view";
    }

    // RequestPart는 파일과 Json을 처리할 때 주로 사용하는 어노테이션이다.
    //
//    @PostMapping("/axios-json-file-view")
//    @ResponseBody
//    public String axiosJsonFilePost(@RequestParam("hello") String hello,
//                                    @RequestParam MultipartFile photo) throws JsonProcessingException {
    // String으로 받은 뒤 수동으로 객체로 변환
//        System.out.println(hello);
//        ObjectMapper om = new ObjectMapper();
//        Hello helloObj = om.readValue(hello, Hello.class);
//        System.out.println(helloObj);
//}


    // formData를 통해 json, file(멀티미디어)을 처리할 때 RequestPart 어노테이션을 많이 사용한다.
    @PostMapping("/axios-json-file-view")
    @ResponseBody
    public String axiosJsonFilePost(@RequestPart("hello") Hello hello, @RequestPart("photo") MultipartFile photo) {
        System.out.println(hello);
        System.out.println(photo.getOriginalFilename());
        return "ok";
    }


    //== case 7. js를 활용한 json 데이터 전송(+여러 file => List<Multipart> 타입으로 받으면 된다.) ==//
    @GetMapping("/axios-json-multi-file-view")
    public String axiosJsonMultiFileView() {
        return "axios-json-multi-file-view";
    }

    @PostMapping("/axios-json-multi-file-view")
    @ResponseBody
    public String axiosJsonMultiFilePost(@RequestPart("hello") Hello hello, @RequestPart("photos") List<MultipartFile> photos) {
        System.out.println(hello);

        for(MultipartFile file : photos) {
            System.out.println(file.getOriginalFilename());
        }
        return "ok";
    }

    //== case 7 end ==//


    //== case 8. 중첩된 JSON 데이터 처리==//
    // {name: 'honggildong', email: 'hong@naver.com', scores:{math:60, music: 70, english: 100}}
    @GetMapping("/axios-nested-json-view")
    public String axiosNestedJsonView() {

        return "axios-nested-json-view";
    }

    // *** 데이터 바인딩에 필요한 것은 기본적으로는 **기본 생성자**와 **Setter**이다.!
    @ResponseBody
    @PostMapping("/axios-nested-json-view")
    public String axiosNestedJsonPost(@RequestBody Student student) {
        System.out.println(student);
        List<Student.Grade> list = student.getGrades();
//        list.stream().map(s -> s.getPoint()).sum();
        return "ok";
    }


}
