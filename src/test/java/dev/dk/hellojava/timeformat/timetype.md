- 자바 시간 타입 정리
- https://gngsn.tistory.com/169

---
- 자바8 이전의 util.Date 클래스 단점
- mutable 하다 (변경 가능 하다)
- 불분명한 네이밍
- 버그 발생 가능성

---
- mutable 하다
- 자바8 이전의 util.Date 클래스는 mutable 하다. 쓰레드 safe 하지 않음
- Date.setTime() 메소드가 있으므로 mutable 함

---
- 불분명한 네이밍
- Date 클래스 인데, 시분초를 포함한다
- Unix Time = Epoch time, POSIX time
- Unix Time = UTC 기준 1970.01.01 00:00:00
- UNIX 운영체제 최초 출시년도가 1971년

---
- 버그 발생 가능성
- 타입 안정성이 없다
- 월이 0부터 시작해서 불편함
```groovy
//코드에서 8,12 라서 8월 이라고 생각되지만, 0부터 시작하므로 9월이 된다
Calendar birthDay = new GregorianCalendar(2022, 8, 12)
//Mon Sep 12 00:00:00 KST 2022 => 2022.09.12
log.info("birthDay={}", birthDay.getTime())
```

---
- 시간 타입 종류
- 기계용 시간 machine time
- 인류용 시간 human time

---
- Instant 클래스
- 머신 타임
- 초단위
- epoch 부터 현재까지 타임스탬프를 표현
- 한 순간의 지점을 다른 순간과 비교하고 싶을때 사용하면 좋음
```groovy
Instant now = Instant.now()
ZonedDateTime zonedDateTime = now.atZone(ZoneId.of("Asia/Seoul"))
//2025-02-28T08:07:11.615501+09:00[Asia/Seoul]
log.info("zonedDateTime={}", zonedDateTime)
```

---
- 휴먼 타임 종류
- 타임존 고려 시간
- 타임존 고려 안하는 시간

---
- LocalDateTime : 타임존 없음
- OffsetDateTime : offset 시간 추가
- ZonedDateTime : offset 시간 추가, 지역 zoneId 정보 추가

---
- xxxDateTime 예시 코드
- TimeTypeTest.java 파일 참고



