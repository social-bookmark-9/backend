<br/>
<br/>
<p align="center">
  <a href="https://bubbled.at" target="_blank">
    <img alt="bubbled-logo" width="600" alt="bubbled logo" src="https://user-images.githubusercontent.com/76833697/162108835-5ad4f3bb-324f-40bc-b0f3-2d333a81265f.png"/>
  </a>
</p>
<p align="center">
    <img alt="Java" src="https://img.shields.io/badge/-Java-007396?logo=Java&logoColor=white"/>
    <img alt="Spring Boot" src="https://img.shields.io/badge/-Spring Boot-6DB33F?logo=Spring Boot&logoColor=white"/>
    <img alt="MySQL" src="https://img.shields.io/badge/-MySQL-4479A1?logo=MySQL&logoColor=white"/>
    <img alt="AWS" src="https://img.shields.io/badge/-AWS-FF9900?logo=AWS Lambda&logoColor=white"/>
    <img alt="IntelliJ IDEA" src="https://img.shields.io/badge/-IntelliJ-000000?logo=IntelliJ IDEA&logoColor=white"/>
</p>

<p align="center">
  <a href="https://bubbled.at/" target="_blank">서비스 페이지</a> •
  <a href="https://github.com/social-bookmark-9/backend/wiki" target="_blank">프로젝트 위키</a> •
  <a href="https://www.figma.com/file/4nSqrNMM5VqxN6AcbFJJI0/%ED%95%AD%ED%95%B4-5%EA%B8%B0-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8?node-id=0%3A1" target="_blank">피그마 (디자인)</a> •
  <a href="https://www.notion.so/bubbled/9-5fe35be95fb440748f062bfab88e0b2c" target="_blank">팀 노션</a>
</p>

<p align="center">
    프로젝트 기간 : 22.03.04 ~ 22.04.08
</p>

<p align="center">
    유지보수 기간 : 22.04.08 ~
</p>

<br/>

---

<br/>

## 목차
- [서비스 소개](#서비스-소개)
- [소개 영상](#소개-영상)
- [주요 기능](#주요-기능)
- [기술 스택](#기술-스택)
- [서비스 아키텍처](#서비스-아키텍처)
- [DB 모델링](#DB-모델링)
- [서비스 API](#서비스-API)
- [핵심 트러블 슈팅](#핵심-트러블-슈팅)
- [그 외 트러블 슈팅](#그-외-트러블-슈팅)
- [향후 목표](#향후-목표)
- [팀원 소개](#팀원-소개)

<br/>

## 서비스 소개

<b>bubbled는 웹페이지 링크 저장 (북마크) 및 공유 플랫폼 입니다.</b>

개발자, 디자이너, 취준생, 프로덕트 매니저, 대학생 등 다양한 사람들이 평소에 
관심있는 분야의 글을 태그를 설정하여 모아놓을 수 있습니다.

또한 다른 사용자들의 모아놓은 양질의 북마크들을 확인할 수 있으며 
이메일을 통해 리마인드 기능을 활용하여 읽어야 할 글들을 확인할 수 있습니다.

<br/>

## 소개 영상

<a href="https://www.youtube.com/watch?v=KkexqkTXsio" target="_blank">
    <img width="600" alt="bubbled" src="https://user-images.githubusercontent.com/76833697/162207218-f505453b-3c2e-47d0-b7e3-e5b7dd3c2cfd.png">
</a>

<br/>

## 주요 기능

- 아티클(북마크) 저장하기  
<img width="600" alt="bubbled" src="https://user-images.githubusercontent.com/76833697/162213729-4278c9ad-5523-48ae-ac6f-d05711b170d1.png">


- 아티클 폴더별 정리  
<img width="600" alt="bubbled" src="https://user-images.githubusercontent.com/76833697/162213742-0aa5f53b-7f89-4bf1-bf67-557bb0c6a5d2.png">


- 다른 사용자의 아티클과 폴더 열람  
<img width="600" alt="bubbled" src="https://user-images.githubusercontent.com/76833697/162216662-814e2423-11b3-4a30-b37d-2fca03e6535b.png">


- 해당 아티클에 대한 리마인더 메일 수신  
<img width="600" alt="bubbled" src="https://user-images.githubusercontent.com/76833697/162213780-7f412459-d0f9-4971-995f-50354ff76977.png">


- 크롬 익스텐션을 통해 해당 웹페이지에서 바로 저장  
<img width="600" alt="bubbled" src="https://user-images.githubusercontent.com/76833697/162216666-1fccd15c-d870-418c-b56d-8e002cab0064.png">

<br/>

## 기술 스택

|         이름         |   버전    |      설명       |
|:------------------:|:-------:|:-------------:|
|        Java        |   1.8   |               |
|    Spring Boot     |  2.4.0  |               |
|       Spring       |  5.3.1  |               |
|  Spring Security   |  2.4.0  |  사용자 인증 및 인가  |
|  Spring Data Jpa   |  2.4.0  |  데이터베이스 ORM   |
|    Spring mail     |  2.4.0  | SMTP 메일 서버 설정 |
|  Spring Cloud AWS  |  2.2.1  |     S3관련      |
|    OAuth client    |  2.4.0  |   OAuth 로그인   |
|       lombok       | 1.18.16 |     개발 편의     |
|        jjwt        |  0.9.1  |   JWT 토큰 조작   |
|       jsoup        | 1.14.3  |   URL 스크래핑    |
|     thymeleaf      |  2.4.0  |  이메일 html 생성  |
|      queryDSL      | 1.0.10  |   동적 쿼리 생성    |

<br/>

## 서비스 아키텍처

<img alt="project-architecture" width="600" src="https://user-images.githubusercontent.com/76833697/161889252-2fa7d4f9-da8f-4b8f-8a94-78f6f2b83545.png"/>

<br/>

## DB 모델링

<img alt="ERD" width="600" src="https://user-images.githubusercontent.com/76833697/162219023-780d86da-2ea9-40c3-86a1-8aacc17bbe80.png"/>

<br/>

## 서비스 API

### 유저

| Feat | Method |         URL         | Request |       Response       |
|:----:|:------:|:-------------------:|:-------:|:--------------------:|
| 로그인  |  POST  |  /api/users/login   |  인가코드(카카오) |       "로그인 성공"       |
| 로그아웃 | POST  |  /api/users/logout  | 리프레시 토큰 |      "로그아웃 성공"       |
| 회원가입 | POST  | /api/users/register | 카카오 아이디  |       "로그인 성공"       |
| 회원탈퇴 | DELETE | /api/users/{userid} |      | "서비스를 이용해주셔서 감사합니다." |
| 토큰 재발급 | POST |  /api/users/token   | 엑세스, 리프레시 토큰 |       "토큰 재발급"       |
|내 정보 | GET | /api/users/check | 헤더: { token } |        myInfo        |
|유저명 중복 확인| GET | /api/users/checkmembername| membername | 사용유무 | 

### 아티클(북마크)

| Feat | Method |         URL         |      Request      |      Response      |
|:----:|:------:|:-------------------:|:-----------------:|:------------------:|
|아티클 생성|POST| /api/articles |    articleData    |     "아티클 생성 성공     |
|아티클 조회 | GET | /api/articles/{articleid}|                   |    "아티클 조회 성공"     |
|아티클 삭제 | DELETE | /api/articles/{articleid} |                   |    "아티클 삭제 성공"     |
|아티클 제목 수정 | PATCH | /api/articles/{articleid}/title |    "제목 수정 내용"     |     "제목 수정 성공"     |
|아티클 해시태그 수정 | PATCH | /api/articles/{articleid}/hashtag|   hashtag1,2,3    |  "아티클 해시태그 수정 성공"  |
|아티클 리뷰 수정 | PATCH | /api/articles/{articleid}/review |    "리뷰 수정 내용"     |   "아티클 리뷰 수정 성공"   |
|아티클 리뷰 공개여부 수정|PATCH|/api/articles/{articleid}/review/hide |                   |    "리뷰 가리기 성공"     |
|아티클 모든 리뷰 조회 | GET | /api/reviews |                   |  "모든 리뷰 가져오기 성공"   |
|아티클 읽은 횟수 증가 | PATCH | /api/articles/{articleid}/readcount |                   | "아티클 읽은 횟수 증가 성공"  |
|아티클 폴더 이동| PATCH | /api/articles/{articleid}/folder | articleFolderName |  "아티클의 폴더 이동 성공"   |
|아티클 타유저 모두 저장 | PATCH | /api/articles/articlefolder/{articlefolderid} | articlefolderName | "타유저 아티클 모두 저장 성공" |

### 아티클 폴더(컬렉션)

|     Feat     | Method |                 URL                  |      Request     |    Response     |
|:------------:|:------:|:------------------------------------:|:----------------:|:---------------:|
|  아티클 컬렉션 생성  |  POST  |          /api/articleFolder          | articleFolderName |   "컬렉션 생성 완료"   |
| 컬렉션 내 아티클 조회 |  GET   | /api/articleFolder/{articleFolderid} |                  | "컬렉션 내 아티클 조회"  |
|  컬렉션 목록 조회   |  GET   |    /api/articleFolder/folderName     |                  |   컬렉션 목록 조회"    |
|컬렉션 삭제 | DELETE | /api/articleFolder/{articleFolderid} |                  |   "컬렉션 삭제 완료"   |
|컬렉션 제목 수정 | PATCH  | /api/articleFolder/{articleFolderid} | articleFolderName | "컬렉션 제목 수정 완료"  |
|컬렉션 좋아요 | PATCH | /api/articleFolder/{articleFolferId}/likes |                  | "컬렉션 좋아요 추가 완료" |
|컬렉션 좋아요 삭제 | DELETE | /api/articleFolder/{articleFolderid}/likes |                  | "컬렉견 좋아요 취 완료"  |

### 검색 페이지

|     Feat     | Method |              URL              |    Request     |  Response   |
|:------------:|:------:|:-----------------------------:|:--------------:|:-----------:|
|아티클 검색 | GET | /api/searchpage/articles/???? |                | "아티클 검색 결과" |
|아티클 폴더 검색 | GET | /api/searchpage/articlesfolders/????|| "아티클 폴더 검색 결과" |

### 메인 페이지

|     Feat     | Method |              URL              |    Request     | Response  |
|:------------:|:------:|:-----------------------------:|:--------------:|:---------:|
|아티클 조회 | GET | /api/mainpage | | 아티클 조회 결과 |
|태그로 아티클 조회 | GET | /api/mainpage/hashtag? | | 아티클 조회 결과 |

### 마이 페이지

|     Feat     | Method |              URL              |  Request   |       Response        |
|:------------:|:------:|:-----------------------------:|:----------:|:---------------------:|
|마이페이지 조회 | GET | /api/mypage/memberid | 토큰, 멤버 아이디 |       유저 정보 결과        |
|프로필 수정(자기소개) | PATCH | /api/mypage/statusmessage | userDesc | "프로필 정보가 업데이트 되었습니다"  |
|프로필 수정(url) | PATCH | /api/mypage/snsurl | urls |   "프로필이 업데이트 되었습니다"   |
|프로필 이미지 수정 | POST | /api/mypage/profileimage | image(multipart) | "프로필 이미지가 업데이트 되었습니다" |
|프로필 닉네임 수정 | PATCH | /api/mypage/nickname | nickname |    "프로필 닉네임 수정 완료"    |
|프로필 관심분야 수정 | PATCH | /api/mypage/hashtag | hashtag1,2,3 |   "프로필 관심분야 수정 완료"    |
|리마인더 이메일 수정 | PATCH | /api/mypage/reminder | email |   "리마인더 이메일 수정 완료"    |

### 리마인더

|     Feat     | Method |              URL              |  Request   |   Response   |
|:------------:|:------:|:-----------------------------:|:----------:|:------------:|
|리마인더 생성 | POST | /api/reminders | article정보 | "리마인더 생성 성공" |
|리마인더 수정 | PATCH | /api/reminders | article정보 | "리마인더 수정 성공" |
|리마인더 삭제 | DELETE | /api/reminders | article정보 | "리마인더 삭제 성공" |
|리마인더 조회 | GET | /api/reminders | | "리마인더 조회 성공" |

<br/>

## 핵심 트러블 슈팅

<details>
<summary>
    <b>부하테스트 진행</b>
</summary>
<img alt="test" width="600" src="https://user-images.githubusercontent.com/87873821/162202178-d3032ea1-0eb8-4880-bafd-2caaee158768.jpg"/>
<img alt="test" width="600" src="https://user-images.githubusercontent.com/87873821/162202453-bf8eba90-2528-4e03-8f6f-308d6856bcd8.jpg"/>
</details>

<details>
<summary>
    <b> 스프링 시큐리티 / JWT 토큰 에러 핸들러</b>
</summary>

Jwt Authentication Filter에서 토큰 만료시 특정 에러 메세지를 클라이언트에 내려주려고 했지만,  
모든 에러가 @RestControllerAdvice의 HandelAccessDenied 항목에 걸리면서 할 수 없었다.

알아보니 Filter단에서 에러가 발생한 경우 스프링 시큐리티를 통과하면서 에러가 단일화가 되어있었다.  
그래서 글로벌 에러 핸들러가 아닌, Filter단에서 독자적으로 에러를 처리할 수 있어야 했다.  
또한 Fiter는 Dispatcher Servelet 보다 앞단에 있기에, Exception Handeler에서 처리할 수 없었다.

![필터, 스프링 구조](https://media.vlpt.us/images/hellonayeon/post/4ec2d270-11bd-43c7-ac02-829b0ae9b280/image.png)

그래서 요청이 들어온다면 Jwt Authentication Filter를 바로 가지 않고 Jwt Exception Filter를  
하나 더 만들어 예외가 던져진다면 해당 Filter에서 Catch해 커스텀한 에러 메세지를 내려줄 수 있게 했다.


```java
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
```
</details>

<br/>

## 그 외 트러블 슈팅

<details>
    <summary>
        <b>업로드 후 S3 서버의 이미지를 찾을수 없음</b>
    </summary>

S3 서버에 프로필 이미지 업로드 테스트를 하던 도중 이미지 업로드는 가능하지만 불러오지 못하는 에러가 있었다.  
새롭게 저장하는 파일의 이름을 log를 찍어가며 살펴보니  
파일 이름에 띄어쓰기가 포함되어 있으면 S3 서버에서는 그대로 저장을 하지만
서버에서 해당 문자열의 공백을 강제로 %20 으로 치환하고 있었다.

해서 해당 파일의 이름을 아래의 코드로 확인하고 넣어주어 공백을 제거해주었다.
~~~
.replace(" ","")
~~~
</details>

<details>
    <summary>
        <b> AWS CloudWatch 도입배경 </b>
    </summary>
<ul>
    <li>EC2 프리티어에서 개발 중 갑작스런 메모리 부족으로 인해 서버가 다운되는 현상 발생</li>
    <li>원인은 늘어가는 코드 로직과 AWS의 CodeDeploy가 실행되면서 메모리가 부족해졌음</li>
    <li>EC2를 업그레이드하고 이런 일을 사전에 방지하기 위해 AWS CloudWatch를 도입하여 메모리 모니터링 실시 </li>
</ul>
</details>

<br/>

## 향후 목표

- [ ] 코드에 주석 설명 달기
- [ ] 쿼리 개선 및 DB 튜닝을 통해 더 높은 강도의 부하테스트 통과
- [ ] 웹소켓을 이용한 실시간 알람 구현
- [ ] Mockito를 이용한 테스트 작성

<br/>

## 팀원 소개

<table>
    <tr>
        <td align="center"><img alt="avatar" src="https://github.com/wowba.png" width="100"></td>
        <td align="center"><img alt="avatar" src="https://github.com/Sudongk.png" width="100"></td>
        <td align="center"><img alt="avatar" src="https://github.com/hyunwoome.png" width="100"></td>
        <td align="center"><img alt="avatar" src="https://github.com/jeongmin-dev.png" width="100"></td>
        <td align="center"><img alt="avatar" src="https://github.com/yonslee.png" width="100"></td>
        <td align="center"><img alt="avatar" src="https://user-images.githubusercontent.com/76833697/162208116-be2363ec-66ea-4c29-a6b9-8048d4033283.png" width="100"></td>
        <td align="center"><img alt="avatar" src="https://user-images.githubusercontent.com/76833697/162208149-505813f3-8587-40fe-b617-8017bab87563.png" width="100"></td>
    </tr>
    <tr>
        <td align="center"><a href="https://github.com/wowba">BE 이영욱</a></td>
        <td align="center"><a href="https://github.com/Sudongk">BE 김수동</a></td>
        <td align="center"><a href="https://github.com/hyunwoome">BE 임현우</a></td>
        <td align="center"><a href="https://github.com/jeongmin-dev">FE 이정민</a></td>
        <td align="center"><a href="https://github.com/yonslee">FE 이윤수</a></td>
        <td align="center"><a href="https://elated-glue-b4c.notion.site/LEEGAIN-PORTFOLIO-e0e1ba571f244c7d9c0a5325ed98295f">DE 이가인</a></td>
        <td align="center"><a href="">DE 이경미</a></td>
    </tr>
</table>

<br/>
