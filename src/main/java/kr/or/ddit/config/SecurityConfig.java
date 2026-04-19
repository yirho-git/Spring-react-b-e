package kr.or.ddit.config;

import jakarta.servlet.DispatcherType;
import kr.or.ddit.security.CustomAccessDeniedHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
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

    // 권한 계층 구조 설정 (ex. 최상위 ADMIN은 하위의 모든 권한을 포함(통과)한다)
    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()    // withDefaultRolePrefix() :: 권한명 앞에 "ROLE_"을 붙여주는 메서드
                .role("ADMIN").implies("MGR")
                .role("MGR").implies("RESIDENT")
                .role("RESIDENT").implies("USER")
                .build();
    }


    // WebSecurityCustomizer :: 정적자원 무시 설정 클래스
    @Bean
    public WebSecurityCustomizer configurer() {
        return (web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                // requestMatchers() :: 괄호 안의 조건을 만족하는 요청을 찾아라
                // toStaticResources() :: 정적 리소스 범주
                // atCommonsLocations() :: 스프링이 정한(약속된) 공통(정적) 자원 위치
                .requestMatchers("/resources/**")); // 중에서 /resources/ 경로에 있는 파일과 모든 깊이의 하위파일들까지 선정
    }


    // 스프링 진영의 세션 기반으로 동작할 모든 요청에 대해서 처리할 필터 체인
    @Order(value = 1)
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/**")
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ASYNC).permitAll()
                        /*
                        보통 사용자가 주소창에 직접 치고 들어오는 건 REQUEST 타입인데, 이건 당연히 보안 검사를 해야겠지?
                         하지만 FORWARD나 ASYNC는 이미 서버 안에서 일어나는 일이라 굳이 두 번 일 시키지 않으려고 쓰는 설정.
                         */
                        .requestMatchers(PASS_URL).permitAll()
                        .anyRequest().authenticated()   // 위의 것들을 제외한 모든 리퀘스트는 인증을 거쳐야한다는 설정
                );
        // 권한이 없는 사용자가 접근했을때, 커스텀 규칙으로 처리
        http.exceptionHandling(
                exception -> exception.accessDeniedHandler(new CustomAccessDeniedHandler())
        );
        // 브라우저의 기본로그인 창 비활성화
        http.httpBasic(basic -> basic.disable());

        http.formLogin(login -> login
                .loginPage("/login.do") // 사용자에게 보여줄 '로그인 페이지' 주소 (GET방식)
                .loginProcessingUrl("/login")
                // 실제 로그인을 검증할 폼데이터 수신 (컨트롤러)주소 (POST방식)
                // 시큐리티가 이 주소로 들어오는 데이터를 가로채서 인증을 진행함
                // 따라서 로그인 폼의 <form action="/login"> 과 반드시 일치해야함
                .successHandler(new CustomLoginSuccessHandler())    // 로그인 성공시 후속처리 담당 핸들러
                .failureHandler(new CustomLoginFailureHandler())    // 로그인 실패시 후속처리 담당 핸들러
        );

        http.logout(
                logout -> logout
                        .logoutUrl("/logout")   // 로그아웃을 수행할 컨트롤러 주소
                        .invalidateHttpSession(true)    // 현재 서버에 저장된 사용자의 세션정보 파기
                        .logoutSuccessUrl("/logout.do")    // 로그인 작업이 끝난후 보낼 페이지
                        .deleteCookies("JSESSIONID")    // JSESSIONID 쿠키도 제거
        );

        return http.build();
    }


}
