package com.sailer.mycalender.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    /***
     * description vs code 에서 프론트 디자인및 프론트서버 실행시 cors 에러 해결을 위해 설정을 추가함.
     * 아래 allowedOrigins 에 localhost 로 적는것과, 127.0.0.1 로 적는것은 동일하게 작동하지 않는다
     * 일부 보안설정이나 애플리케이션 설정에서 둘을 다르게 취급할 수 있다고 한다.
     * 따라서 허용할 url 을 둘다 적어주는게 좋을 듯 하다.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:9094",
                        "https://localhost:9094")    // 허용할 출처. 프로토콜, 호스트, 포트번호 세개 모두 명시해줘야함!
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP Method
                .allowedHeaders("*")
                .allowCredentials(true) // 쿠키 인증 요청 허용
                .maxAge(3600);  // 원하는 시간만큼 pre-flight 리퀘스트를 캐싱
    }
}
