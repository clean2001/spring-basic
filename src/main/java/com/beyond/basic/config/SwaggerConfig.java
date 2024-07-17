package com.beyond.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    // SWAGGER 구성의 핵심 기능 클래스
    // Docket을 리턴함으로써 싱글톤 객체로 생성
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 어떤 컨트롤러 또는 어떤 API swagger 문서에 포함시킬지를 정함
                .select()
                // 모든 RequestHandler(controller)를 문서화 대상으로 선택한다는 설정
                .apis(RequestHandlerSelectors.any())
                // 특정 Path만 문서화 대상으로 하겠다. (/rest로 시작하는 API만 사용하겠다는 뜻)
                // * : 보통은 집계. / ** : 자손까지 포함하겠다.
                .paths(PathSelectors.ant("/rest/**"))
                .build();
    }
}
