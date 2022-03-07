package com.sparta.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable() // csrf 보안 토큰 disable처리.
                .authorizeRequests() // 요청에 대한 사용권한 체크
                .antMatchers("/**").permitAll()
                .anyRequest().permitAll(); // 그외 나머지 요청은 누구나 접근 가능
    }
}
