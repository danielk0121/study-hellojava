### 문자열 처리
- 문자열 리버스
- 문자열 대소문자 변경
- 한 문자열 리플레이스
- 여러 문자열 리플레이스
- 정규식 리플레이스
- 문자열 정렬
- 문자열 압축
- string, char, 아스키 코드
- 위 작업들을 스트림으로 처리
- CharSequence, String 차이

---
- 자주 쓰는 예제 정리
- 숫자 문자열 변환
- 랜덤 문자, 숫자 생성
- 랜덤 문자열 생성
- 문자열 셔플

---
- 숫자 문자열을 숫자 타입으로 변환
```
String str = "12345"
char ch = str.charAt(i)
int num = ch - '0'
```

---
- 랜덤 문자, 숫자
```
//26 => a-z 26개
//nextInt(int bound) => [0, bound-1]
Random rand = new Random()
char uc = (char) (rand.nextInt(26) + 'A'); //랜덤 대문자
char lc = (char) (rand.nextInt(26) + 'a'); //랜덤 대문자
int rInt = rand.nextInt(10); //랜덤 숫자 [0,9]
```

---
- 랜덤 문자 1개 추출
```
String str = "asdfqwer";
Random rand = new Random();
char ch = str.charAt(rand.nextInt(str.length()));
```
