package kr.or.ddit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

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

}
