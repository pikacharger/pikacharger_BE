# ⚡️피카충전

**[ 언제 어디서든 충전소를 찾아주는 피카 충전! ]**
<br>
내 주변 충전소들을 쉽게 알아보고 충전소가 없는 지역에서는 개인의 충전기를 사용할 수 있도록 중계해주는 기능을 제공하는 ‘전기차 사용자를 위한 충전기 거래 플랫폼’.


<br>

## 프로젝트 목적

- 전국적으로 전기차 충전기 대수는 30만대를 초과했지만 미흡한 급속 충전기 보급과 도시에 집중된 충전기 분포로 인해 전기차 충전 인프라에 대한 개선 요구가 계속 되는 상황.
- 이 때문에 개인용 충전선, 충전기를 사용하는 인구도 증가함.
- 공공/민간 충전소의 위치를 알 수 있음과 동시에 해당 지역에서 개인용 충전기 보유자와 거래하여 충전기를 빌리고 사용금액을 지불하는 형식으로 공영 주차장으로 제한되었던 장소의 한계를 뛰어넘을 수 있도록 함.

<br>

## 개발 팀원

- Back-end : 남지원님[PM], 유영무님, 조민지님, 주인혁님
- Front-end : 김진규님, 박재희님, 예은선님

<br>

## 개발 기간

2024.04.08 ~ 2024.05.10

<br>

## 백엔드 or 프론트엔드 팀원 역할

- 남지원 [PM]
    - 데이터 전처리, 이미지 / 리뷰 기능 / 서버 배포
- 유영무
    - 데이터 전처리, 충전소 / 즐겨찾기 기능 / 서버 배포
- 조민지
    - 실시간 채팅 / 서버 배포
- 주인혁
    - user 관련 기능 / 서버 배포

<br>

## DB 모델링

(사진첨부예정)

<br>

## Directory 구조

```
.
│   │── java
│   │   ├── pikacharger
│   │   │   ├── common.mapper
│   │   │   ├── config
│   │   │   ├── domain
│   │   │   │   ├── charger
│   │   │   │   ├── chargertype
│   │   │   │   ├── chat
│   │   │   │   ├── common
│   │   │   │   ├── email
│   │   │   │   ├── favorite
│   │   │   │   ├── image
│   │   │   │   ├── review
│   │   │   │   ├── user
│   │   │   ├── exceptional
│   │   │   ├── security
│   │   │   │   ├── config
│   │   │   │   ├── jwt
│   │   │   │   ├── oauth
│   │   │   ├── util
│   │   │   ├── websocket
│   │── resources
└── test

```

<br>

## 백엔드 기술 스택

- 백엔드 : Java 17, spring boot 3.2.4, jpa, Oauth2.0, jwt, aws s3, web socket
- 데이터베이스 : AWS RDS(MySQL)
- 서버 배포 : NGINX, Docker, AWS EC2, Redis

<br>

## 프로젝트 사이트

- Youtube 시연영상 : 영상주소 추가예정
- 피카충전 주소 : [피카충전 링크](https://pikacharger.store/)

(사이트 움짤 or 사진 추가예정)

<br>

## API 명세서

[스웨거 링크 1](http://ec2-43-203-7-98.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/index.html#/(%EC%B6%A9%EC%A0%84%EC%86%8C)/createCharger)
[스웨거 링크 2](http://ec2-43-203-7-98.ap-northeast-2.compute.amazonaws.com:8081/swagger-ui/index.html#/(%EC%B6%A9%EC%A0%84%EC%86%8C)/createCharger)
