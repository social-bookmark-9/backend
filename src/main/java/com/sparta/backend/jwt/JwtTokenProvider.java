package com.sparta.backend.jwt;

import com.sparta.backend.oauthDto.TokenDto;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
// JWT를 생성하고 검증하는 컴포넌트!
public class JwtTokenProvider {

    // 시크릿 키. 실전에는 따로 환경변수 파일을 만들어서 보안을 생각하자.
    @Value("${secret-key}")
    private String secretKey;

    // 테스트용 엑세스 토큰 발급 시간
//    private long accessTokenValidTime = 1 * 1 * 60 * 1000L; // 1분
//    private long accessTokenValidTime = 1 * 10 * 60 * 1000L; // 10분
    private long accessTokenValidTime = 24 * 60 * 60 * 1000L; // 하루

    // 테스트용 리프레시 토큰 발급 시간
//    private long refreshTokenValidTime = 1 * 1 * 1 * 10 * 1000L;
    private long refreshTokenValidTime = 30 * 24 * 60 * 60 * 1000L; // 한달

    private final UserDetailsService userDetailsService;

    // 객체 초기화, secretKey를 Base64로 인코딩
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성 (Access, Refresh Token 포함)
    public TokenDto createAccessRefreshToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();

        // Access Token 생성
        String accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return TokenDto.builder()
                .grantType("bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpireDate(accessTokenValidTime)
                .build();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {

        // JWT 에서 Claims 추출
        Claims claims = parseClaims(token);

        // 권한 정보 확인
        if (claims.get("roles") == null) {
            throw new IllegalArgumentException("권한이 없습니다");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // JWT 토큰 복호화해서 가져오기
    public Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값"
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            log.info("토큰 검증 성공");
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw new JwtException("Expired");
        } catch (MalformedJwtException e) {
            log.info("잘못된 JWT 토큰입니다.");
            throw new JwtException("잘못된 JWT 서명입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 JWT 토큰입니다.");
            throw new JwtException("지원하지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("잘못된 JWT 토큰입니다.");
            throw new JwtException("JWT 토큰이 잘못되었습니다.");
        } catch (SignatureException e) {
            log.info("JWT 서명이 잘못되었습니다.");
            throw new JwtException("잘못된 JWT 서명입니다.");
        } catch (Exception e) {
            // 토큰이 들어오지 않았을 경우 AccessDeniedHandler를 통해 403 에러를 내보낸다.
            log.info("토큰 검증 실패");
        }
        return false;
    }
}
