### 문자열 기본 함수 정리

---
```java
class Main {
    void main() {
        //대소문자
        "ASDF".toLowerCase();
        "asdf".toUpperCase();
        
        //일치, 검증
        "asdf".equals("ASDF");
        "asdf".equalsIgnoreCase("ASDF");
        "asdf".contains("sd");
        "asdf".startsWith("as");
        "asdf".endsWith("df");
        "asdf1".compareTo("asdf2"); //사전순 비교
        
        //공백
        "".isEmpty();
        "".isBlank();
        " asdf ".trim();

        //탐색
        "asdf".length(); //4
        "asdf".charAt(2); //'d'
        "asdf".indexOf((int) 'd'); //2
        "asdf".indexOf("d"); //2

        //indexOf, lastIndexOf
        "asdfasdf".indexOf('d'); //2, 처음 나오는 위치
        "asdfasdf".lastIndexOf('d'); //6, 마지막 나오는 위치
        "asdfasdf".lastIndexOf("d"); //6

        //replace
        "asdf".replace('a','1'); //1sdf
        "asdf".replace("as","12"); //12df
        "asdfasdf".replaceFirst("as", "12"); //12dfasdf, 처음 한번만
        "asdfasdf".replaceAll("as", "12"); //12df12df, 모두

        //matches
        "1234".matches("^[0-9]*$"); //true, 정규식 판별
        
        //repeat, 문자열 반복
        "asdf".repeat(2); //asdfasdf
        
        //split, substring
        "asdf|qwer".split("\\|"); //[asdf, qwer]
        "asdfqwer".substring(2); //dfqwer
        "asdfqwer".substring(2, 4); //df, endIndex 문자열 제외됨
        
        //타입 변경
        char[] charArray = "asdf".toCharArray();
        byte[] bytes = "asdf".getBytes(StandardCharsets.UTF_8);

        //chars()
        IntStream chars = "asdf".chars(); //char 스트림 아니고 IntStream
        chars.forEach(c -> System.out.print(((char) c))); //asdf
        
        //valueOf
        String.valueOf((int) 10);
        String.valueOf((long) 10L);
        String.valueOf((float) 10.2f);
        String.valueOf((double) 10.3d);
        String.valueOf((char) 'a');
        String.valueOf(new char[]{'a','s','d','f'}); //asdf
        String.valueOf((boolean) true);
        
        //copyValueOf => 부분 복사, char[] 사용
        //subString => 문자열 부분 복사, String 사용
        char[] charArray = "asdfqwer".toCharArray();
        System.out.println(String.copyValueOf(charArray, 2, 3)); //dfq, index 2 부터, 3개
        
        //strip
        //trim 단어뜻 : 손질, 정돈
        //strip 단어뜻 : 벗겨내다, 떼어버리다
        //indent 단어뜻 : 들여쓰기
        //trim => \u0020 이하의 공백 제거
        //strip => 유니코드 공백 모두 제거
        "  asdf  ".trim(); //공백 제거
        "  asdf  ".strip(); //공백 제거
        "  asdf  ".stripLeading(); //앞공백 제거
        "  asdf  ".stripTrailing(); //뒤공백 제거
        
        //join => 구분자로 붙이기
        String.join(",", "asdf|qwer".split("\\|")); //asdf,qwer
        
        //lines() => String 스트림 리턴
        "asdf\nqwer".lines().forEach(System.out::println);
        
        //indent() java12 추가, 들여쓰기 추가
        //stripIndent() java13 추가, 들여쓰기 제거
    }
}
```

---
