package com.sparta.backend.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // JWT 토큰이 정상적이지 않거나 만료되었을 경우. 즉, 토큰 인증 처리가 불가능한 경우 401
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
