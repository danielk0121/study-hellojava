### 문자열 처리

---
- 1단계
- String 기본 함수 정리

---
- 2단계
- 숫자 문자열을 숫자 타입으로 변환
- 랜덤 문자, 랜덤 숫자 문자열 생성
- ! 문자열 리버스
- 문자열 셔플
- 문자열 정렬
- 문자열 압축

---
- 3단계
- 위 작업들을 스트림으로 처리
- CharSequence, String 차이

---
- String 기본 함수 정리
- [string-basic.md](string-basic.md)

---
- 숫자 문자열을 숫자 타입으로 변환
```java
String str = "12345"
char ch = str.charAt(i)
int num = ch - '0'
```

---
- 랜덤 문자, 숫자 생성
```java
//a-z 26개, nextInt(int bound) => [0, bound-1]
Random rand = new Random();
char uc = (char) (rand.nextInt(26) + 'A'); //랜덤 대문자
char lc = (char) (rand.nextInt(26) + 'a'); //랜덤 소문자
int rn = rand.nextInt(10); //랜덤 숫자 [0,9]
```

---
- 문자 1개 랜덤으로 선택
```java
String str = "asdfqwer";
Random rand = new Random();
int idx = rand.nextInt(str.length());
char ch = str.charAt(idx);
```

---
- 문자열 리버스
```java

```

