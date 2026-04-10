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
                .requestMatchers("/resources/**"));
    }



}
