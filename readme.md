![](https://bubbled-profile-image.s3.ap-northeast-2.amazonaws.com/email-image/logoDark.png) 
# BUBBLE_D

## 🌟 서비스 소개
BUBBLED는 웹페이지 링크 저장 (북마크) 및 공유 플랫폼 입니다.  
개발자, 디자이너, 취준생, 프로덕트 매니저, 대학생 등 다양한 사람들이 평소에 관심있는 분야의 글을 태그를 설정하여 모아놓을 수 있습니다.  
또한 다른 사용자들의 모아놓은 양질의 북마크들을 확인할 수 있으며 이메일을 통해 리마인드 기능을 활용하여 읽어야 할 글들을 확인할 수 있습니다.

## 👫 팀원 소개 &  📅 제작 기간

- **Back End**
    - 임현우(팀장)([https://github.com/hyunwoome](https://github.com/hyunwoome))
    - 이영욱([https://github.com/wowba](https://github.com/wowba))
    - 김수동([https://github.com/Sudongk](https://github.com/Sudongk))
- **Front End**
    - 이정민(부팀장)([https://github.com/jeongmin-dev](https://github.com/jeongmin-dev))
    - 이윤수([https://github.com/yonslee](https://github.com/yonslee))
- **Deginer**
    - 이가인
    - 이경미
- **Production Period**
    - 2022 / 03 / 04 ~ 2022 / 04 / 08 
    - 현재 유지보수중...

## 🏢 Project Architecture
![프로젝트 아키텍쳐](https://user-images.githubusercontent.com/76833697/161889252-2fa7d4f9-da8f-4b8f-8a94-78f6f2b83545.png)

## 🔧 Tech Stack

- JAVA 8
- Spring Boot 2.4.0
- Amazon AWS
- Amazon S3
- Github Actions
- Mysql 8.0

## 📌 Library

|     library     |   description   |
|:---------------:|:---------------:|
| Spring Security |    로그인 기능 구현    |
|   Validation    |     유효성 검사      |
| Springboot-mail |     메일서버 구현     |
| Spring Data JPA |    개발생산성 증대     |
|     Lombok      |    개발생산성 증대     |
|      JJWT       |    jwt 토큰 발급    |
|      Jsoup      | URL meta 태그 크롤링 |
|    Thymeleaf    |  이메일 HTML 커스텀   |
|      Slf4j      |    로깅 전략 구현     |
|    Querydsl     |    복잡한 쿼리 구현    |

## 🔍  API

## [BUBBLED API 리스트](https://bubbled.notion.site/fd1d1c5c6a3c42bbbfe18d4cf029c284?v=e7f88371b99b4da895856e98dc20432a)

## 📋 ERD

![ERD](https://user-images.githubusercontent.com/76833697/160228811-a41c505e-2ef1-4fd2-8c9a-38f5a94bf701.png)

## 💥 핵심 트러블 슈팅

<details>
<summary><b>📈 부하테스트 진행</b></summary>

![부하테스트 전](https://user-images.githubusercontent.com/87873821/162202178-d3032ea1-0eb8-4880-bafd-2caaee158768.jpg)
![부하테스트 후](https://user-images.githubusercontent.com/87873821/162202453-bf8eba90-2528-4e03-8f6f-308d6856bcd8.jpg)
</details>

<details>
<summary><b> 🔏 스프링 시큐리티 / JWT 토큰 에러 핸들러</b></summary>

Jwt Authentication Filter에서 토큰 만료시 특정 에러 메세지를 클라이언트에 내려주려고 했지만,  
모든 에러가 @RestControllerAdvice의 HandelAccessDenied 항목에 걸리면서 할 수 없었다.

알아보니 Filter단에서 에러가 발생한 경우 스프링 시큐리티를 통과하면서 에러가 단일화가 되어있었다.  
그래서 글로벌 에러 핸들러가 아닌, Filter단에서 독자적으로 에러를 처리할 수 있어야 했다.  
또한 Fiter는 Dispatcher Servelet 보다 앞단에 있기에, Exception Handeler에서 처리할 수 없었다.

![필터, 스프링 구조](https://media.vlpt.us/images/hellonayeon/post/4ec2d270-11bd-43c7-ac02-829b0ae9b280/image.png)

그래서 요청이 들어온다면 Jwt Authentication Filter를 바로 가지 않고 Jwt Exception Filter를  
하나 더 만들어 예외가 던져진다면 해당 Filter에서 Catch해 커스텀한 에러 메세지를 내려줄 수 있게 했다.

~~~java
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(req, res); // JwtAuthenticationFilter로 이동
        } catch (JwtException ex) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, res, ex);
        }
    }
    
    // 에러를 캐치할 경우 해당 Response를 클라이언트에 내려준다!
    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");

        JSONObject responseJson = new JSONObject();
        responseJson.put("HttpStatus", HttpStatus.UNAUTHORIZED);
        responseJson.put("message", ex.getMessage());
        responseJson.put("status", false);
        responseJson.put("statusCode", 401);
        responseJson.put("code", "401");
        response.getWriter().print(responseJson);
    }
}
~~~

</details>

## ❓ 그 외 트러블 슈팅

<details>
<summary><b>업로드 후 S3 서버의 이미지를 찾을수 없음</b></summary>

S3 서버에 프로필 이미지 업로드 테스트를 하던 도중 이미지 업로드는 가능하지만 불러오지 못하는 에러가 있었다.  
새롭게 저장하는 파일의 이름을 log를 찍어가며 살펴보니  
파일 이름에 띄어쓰기가 포함되어 있으면 S3 서버에서는 그대로 저장을 하지만
서버에서 해당 문자열의 공백을 강제로 %20 으로 치환하고 있었다.

해서 해당 파일의 이름을 아래의 코드로 확인하고 넣어주어 공백을 제거해주었다.
~~~
.replace(" ","")
~~~


</details>

## 💹 향후 프로젝트 목표

- 쿼리 개선 및 DB 튜닝을 통해 더 높은 강도의 부하테스트 통과