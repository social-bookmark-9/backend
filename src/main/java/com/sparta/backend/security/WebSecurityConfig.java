package com.sparta.backend.security;

import com.sparta.backend.jwt.JwtAuthenticationFilter;
import com.sparta.backend.jwt.JwtExceptionFilter;
import com.sparta.backend.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 토근 생성 및 제공자 DI.
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtExceptionFilter jwtExceptionFilter;

    // 스웨거 적용
    @Override public void configure(WebSecurity web) {
        web     // 스웨거 사용 허용
                .ignoring() .mvcMatchers("/swagger-ui.html/**",
                "/configuration/**", "/swagger-resources/**",
                        "/v2/api-docs","/webjars/**",
                        "/webjars/springfox-swagger-ui/*.{js,css}")

                // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
                .antMatchers("/h2-console/**")
                // image 폴더를 login 없이 허용
                .antMatchers("/images/**")
                // css 폴더를 login 없이 허용
                .antMatchers("/css/**");
    }

    @Bean // 비밀번호 암호화 해서 저장하는 Bean 등록하기.
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic().disable() // rest api 만을 고려
                .csrf().disable() // csrf 보안 토큰 disable처리.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 역시 사용하지 않습니다.
                    .and()
                .cors() // CORS 설정 파일은 WebConfig
                    .and()
                .authorizeRequests() // 요청에 대한 사용권한 체크
//                    .antMatchers("api/users/test").hasRole("USER") // 토큰 유효성 테스트용 401
//                    .antMatchers("api/admins/test").hasRole("ADMIN") // 토큰 권한 테스트용 403
//                    .antMatchers("/**").permitAll() // 개발기간동안 우선 열어놓기.
                .anyRequest().permitAll() // 어떤 요청에도 보안검시 진행.
                    .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                // 토큰 만료를 확인하기 위한 필터
                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);
    }
}
