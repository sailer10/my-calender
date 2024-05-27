package com.sailer.mycalender.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    /***
     * description vs code 에서 별도 프론트 디자인시 cors 에러 해결을 위해 설정을 추가함.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5500", "http://127.0.0.1:5500")    // 허용할 출처. 프로토콜, 호스트, 포트번호 세개 모두 명시해줘야함!
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP Method
                .allowCredentials(true) // 쿠키 인증 요청 허용
                .maxAge(3600);  // 원하는 시간만큼 pre-flight 리퀘스트를 캐싱
    }
}
