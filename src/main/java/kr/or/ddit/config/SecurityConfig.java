package kr.or.ddit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // 인증객체 없어도(모두가) 접근할 수 있는 URL => JSP페이지
    private static final String[] PASS_URL = {
            "/",
            "/index.html",
            "/login",
            "/error",
            "/login.do",
            "/.well-known/**"
    };

    private static final String[] REDIRECT_PASS_URL = {
            "/api/react/checkId.do",
            "/api/react/signup.do",
            "/api/react/signin.do",
            "/api/react/notice/list"
    };

    // WebSecurityCustomizer :: 정적자원 무시 설정 클래스
    @Bean
    public WebSecurityCustomizer configurer(){
        return (web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                // requestMatchers() :: 괄호 안의 조건을 만족하는 요청을 찾아라
                // toStaticResources() :: 정적 리소스 범주
                // atCommonsLocations() :: 스프링이 정한(약속된) 공통(정적) 자원 위치
                .requestMatchers("/resources/**")); // 중에서 /resources/ 경로에 있는 파일과 모든 깊이의 하위파일들까지 선정
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf( csrf -> csrf.disable());
    }



}
