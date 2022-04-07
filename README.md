<br/>
<p align="center">
  <a href="https://bubbled.at" target="_blank">
    <img alt="bubbled-logo" height="300" alt="bubbled logo" src="https://user-images.githubusercontent.com/76833697/162108835-5ad4f3bb-324f-40bc-b0f3-2d333a81265f.png"/>
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
  <a href="https://www.figma.com/file/4nSqrNMM5VqxN6AcbFJJI0/%ED%95%AD%ED%95%B4-5%EA%B8%B0-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8?node-id=0%3A1" target="_blank">와이어 프레임</a> •
  <a href="https://www.notion.so/bubbled/9-5fe35be95fb440748f062bfab88e0b2c" target="_blank">팀 노션</a>
</p>

---

## 목차
- [서비스 소개](#서비스-소개)
- [소개 영상](#소개-영상)
- [주요 기능](#주요-기능)
- [기술 스택](#기술-스택)
- [서비스 아키텍처](#서비스-아키텍처)
- [팀원 소개](#팀원-소개)

## 서비스 소개
**bubbled**는 웹페이지 링크 저장 (북마크) 및 공유 플랫폼 입니다.

개발자, 디자이너, 취준생, 프로덕트 매니저, 대학생 등 다양한 사람들이 평소에 
관심있는 분야의 글을 태그를 설정하여 모아놓을 수 있습니다.

또한 다른 사용자들의 모아놓은 양질의 북마크들을 확인할 수 있으며 
이메일을 통해 리마인드 기능을 활용하여 읽어야 할 글들을 확인할 수 있습니다.

## 소개 영상

<p align="center">
    <a href="https://www.youtube.com/watch?v=KkexqkTXsio">
        <img width="500" alt="bubbled" src="https://user-images.githubusercontent.com/76833697/162207218-f505453b-3c2e-47d0-b7e3-e5b7dd3c2cfd.png">
    </a>
</p>

## 주요 기능

- 아티클(링크, 북마크) 저장
- 아티클 폴더별 정리
- 다른 사용자의 아티클과 폴더 열람
- 검색기능으로 아티클, 폴더 검색
- 해당 아티클에 대한 리마인더 메일 수신
- 크롬 익스텐션을 통해 해당 웹페이지에서 바로 저장


## 기술 스택

|         이름         |   버전    |      설명       |
|:------------------:|:-------:|:-------------:|
|        Java        |   1.8   |               |
|    Spring Boot     |  2.4.0  |               |
|       Spring       |  5.3.1  |               |
|  Spring Security   |  2.4.0  |  사용자 인증 및 인가  |
|  Spring Data Jpa   |  2.4.0  |  데이터베이스 ORM   |
|    Spring mail     |         | SMTP 메일 서버 설정 |
|  Spring Cloud AWS  |  2.2.1  |     S3관련      |
|    OAuth client    |  2.4.0  |   OAuth 로그인   |
|       lombok       | 1.18.16 |     개발 편의     |
|        jjwt        |  0.9.1  |   JWT 토큰 조작   |
|       jsoup        | 1.14.3  |   URL 스크래핑    |
|     thymeleaf      |         |  이메일 html 생성  |
|      queryDSL      | 1.0.10  |   동적 쿼리 생성    |

## 서비스 아키텍처
![서비스 아키텍처](https://user-images.githubusercontent.com/76833697/161889252-2fa7d4f9-da8f-4b8f-8a94-78f6f2b83545.png)

## 향후 목표

- 쿼리 개선 및 DB 튜닝을 통해 더 높은 강도의 부하테스트 통과

## 팀원 소개

<table>
    <tr>
        <td align="center"><img src="https://github.com/wowba.png" width="100"></td>
        <td align="center"><img src="https://github.com/Sudongk.png" width="100"></td>
        <td align="center"><img src="https://github.com/hyunwoome.png" width="100"></td>
        <td align="center"><img src="https://github.com/jeongmin-dev.png" width="100"></td>
        <td align="center"><img src="https://github.com/yonslee.png" width="100"></td>
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