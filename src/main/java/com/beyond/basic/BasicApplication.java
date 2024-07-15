package com.beyond.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication 어노테이션의 역할이 뭐냐 ==> @ComponentScan 어노테이션이 붙어 있다.
// @ComponentScan => 이게 컴포넌트 스캐너이다. 해당 어노테이션을 통해 컴포넌트 스캐닝을 수행한다.
@SpringBootApplication
public class BasicApplication {
	public static void main(String[] args) {
		SpringApplication.run(BasicApplication.class, args);
	}

}
