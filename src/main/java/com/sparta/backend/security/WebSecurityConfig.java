package com.sparta.backend.security;

import com.sparta.backend.jwt.JwtAuthenticationFilter;
import com.sparta.backend.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 토근 생성 및 제공자 DI.
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

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
                    .antMatchers("/user/test").hasRole("USER") // 토큰 유효성 테스트용 
                    .antMatchers("/admin/test").hasRole("ADMIN") // 토큰 유효성 테스트용
                    .antMatchers("/**").permitAll() // 개발기간동안 우선 열어놓기.
                    .anyRequest().permitAll() // 그외 나머지 요청은 누구나 접근 가능
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .accessDeniedHandler(customAccessDeniedHandler)
                .and()
                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
}
