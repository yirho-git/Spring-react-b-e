package kr.or.ddit.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.security.Principal;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    // 사용자가 정의한 접근 거부 처리자

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("## CustomAccessDeniedHandler.handle() 실행...!");
        log.info("## AccessDeniedException info -------------------");

        Principal principal = request.getUserPrincipal();

        if(principal != null){
            // 현재 로그인한 사용자의 ID
            String username = principal.getName();

            String userLevel = "UNKNOWN";   // 방어코드, 디버깅용
            if (request.isUserInRole("ROLE_ADMIN")) userLevel = "ADMIN";
            else if (request.isUserInRole("ROLE_MGR")) userLevel = "MANAGER";
            else if (request.isUserInRole("ROLE_RESIDENT")) userLevel = "RESIDENT";
            else if (request.isUserInRole("ROLE_USER")) userLevel = "USER";

            log.info("## 접속자 ID : [{}] / 권한 : [{}]", username, userLevel);
        }

        log.info(accessDeniedException.getMessage());
        response.sendRedirect("/accessError");
    }

}
