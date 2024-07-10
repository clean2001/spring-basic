package com.beyond.basic.domain;

import lombok.*;

// lombok 라이브러리를 통해 getter, setter 어노테이션을 사용한다.
//@Getter
//@Setter
//@ToString
@AllArgsConstructor // 모든 매개변수를 사용한 생성자를 만드는 어노테이션
@NoArgsConstructor // 기본 생성자를 만드는 어노테이션
@Data // getter, setter, toString, (equals, hashCode) 등을 포함
public class Hello {
    private String name;
    private String email;
    private String password;


//    @Override
//    public String toString() {
//        return "Hello{" +
//                "name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                '}';
//    }
}
