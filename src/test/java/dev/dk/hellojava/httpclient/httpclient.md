### http client

---
- todo
- == GET, POST, PUT, DELETE 예제 추가
- 2xx, 4xx, 5xx 처리

---
- 추가할 클라이언트
- == RestTemplate
- WebClient
- OKHttp
- OpenFeign

---
- RestTemplate GET 요청
- todo 문서화

---
- RestTemplate POST 요청
- postForObject() : 요청 후, 응답 본문을 지정된 클래스 유형으로 반환
- postForEntity() : 요청 후, 응답을 ResponseEntity 로 반환
- postForLocation() : 요청 후, Location 헤더 값을 반환 => 생략
- exchange() : 헤더 포함 요청 후, ResponseEntity 로 반환

---
- RestTemplate PUT, DELETE 요청
- exchange() 사용
- rt.put(), rt.delete() 메소드 있지만...

---



