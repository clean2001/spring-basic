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

    // Builder 패턴 직접 구현
    // 빌터 패턴을 구현하기 위해서는 빌더 적용 대상 생성자가 필요하다.
    public Hello(HelloBuilder helloBuilder) {
        this.name = helloBuilder.name;
        this.email = helloBuilder.email;
        this.password = helloBuilder.password;
    }

    public static HelloBuilder builder() {
        return new HelloBuilder();
    }
    public static class HelloBuilder {
        private String name;
        private String email;
        private String password;

        public HelloBuilder name(String name) {
            this.name = name;
            return this;
        }

        public HelloBuilder email(String email) {
            this.email = email;
            return this;
        }

        public HelloBuilder password(String password) {
            this.email = password;
            return this;
        }

        public Hello build() {
            return new Hello(this);
        }
    }
}
