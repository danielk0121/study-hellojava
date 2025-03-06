package dev.dk.hellojava.string;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SuppressWarnings({"ConstantValue", "MismatchedStringCase", "EqualsWithItself", "UnnecessaryUnicodeEscape"})
public class StringBasicTest {

    @Test
    void lowerUpper() {
        //대소문자
        assertThat("ASDF".toLowerCase()).isEqualTo("asdf");
        assertThat("asdf".toUpperCase()).isEqualTo("ASDF");
    }

    @Test
    void equals() {
        //일치, 검증
        assertThat("asdf".equals("ASDF")).isFalse();
        assertThat("asdf".equalsIgnoreCase("ASDF")).isTrue();
        assertThat("asdf".contains("sd")).isTrue();
        assertThat("asdf".startsWith("as")).isTrue();
        assertThat("asdf".endsWith("df")).isTrue();

        //equals : 같은지 비교, true false
        //compareTo : 사전순 비교 결과, 사전순 왼쪽 빼기 오른쪽, 같으면 0
        assertThat("asdf1".compareTo("asdf2")).isLessThan(0); //0 보다 작음
        assertThat("asdf2".compareTo("asdf1")).isGreaterThan(0); //0 보다 큼
        assertThat("asdf".compareTo("asdf")).isEqualTo(0); //0
    }

    @Test
    void blank() {
        //공백 처리
        assertThat("".isEmpty()).isTrue();
        assertThat(" ".isEmpty()).isFalse();
        assertThat("".isBlank()).isTrue();
        assertThat(" ".isBlank()).isTrue();
        assertThat("asdf".trim()).isEqualTo("asdf");
        assertThat(" asdf ".trim()).isEqualTo("asdf");
        assertThat("asdf\n".trim()).isEqualTo("asdf");
        assertThat("\nasdf\n".trim()).isEqualTo("asdf");
    }

    @SuppressWarnings("OnlyOneElementUsed")
    @Test
    void indexOf() {
        //탐색
        assertThat("asdf".length()).isEqualTo(4);
        assertThat("asdf".charAt(2)).isEqualTo('d');
        assertThat("asdf".indexOf((int) 'd')).isEqualTo(2);
        assertThat("asdf".indexOf("d")).isEqualTo(2);

        assertThat("asdfasdf".indexOf('d')).isEqualTo(2);
        assertThat("asdfasdf".lastIndexOf('d')).isEqualTo(6);
        assertThat("asdfasdf".lastIndexOf("d")).isEqualTo(6);
    }

    @Test
    void replace() {
        System.out.println("asdf".replace('a','1')); //1sdf
        System.out.println("asdf".replace("as","12")); //12df
        System.out.println("asdfasdf".replaceFirst("as", "12")); //12dfasdf, 처음 한번만 replace
        System.out.println("asdfasdf".replaceAll("as", "12")); //12df12df, 모두 replace
    }

    @Test
    void match() {
        System.out.println("1234".matches("^[0-9]*$")); //true, 정규식 판별
    }

    @Test
    void repeat() {
        System.out.println("asdf".repeat(2)); //asdfasdf
    }

    @Test
    void split_substring() {
        String[] array = "asdf|qwer".split("\\|");
        System.out.println(Arrays.asList(array)); //[asdf, qwer]

        //as dfq wer
        //01 234 567
        System.out.println("asdfqwer".substring(2)); //dfqwer
        System.out.println("asdfqwer".substring(2, 4)); //df, endIndex 문자열 제외됨
    }

    @Test
    void change_type() {
        char[] charArray = "asdf".toCharArray();
        byte[] bytes = "asdf".getBytes(StandardCharsets.UTF_8);

        IntStream chars = "asdf".chars(); //char 스트림 아니고 IntStream
        chars.forEach(c -> System.out.print(((char) c))); //asdf
    }

    @Test
    void valueOf() {
        System.out.println(String.valueOf((int) 10)); //10
        System.out.println(String.valueOf((long) 10L)); //10
        System.out.println(String.valueOf((float) 10.2f)); //10.2
        System.out.println(String.valueOf((double) 10.3d)); //10.3
        System.out.println(String.valueOf((char) 'a'));  //a
        System.out.println(String.valueOf(new char[]{'a','s','d','f'})); //asdf
        System.out.println(String.valueOf((boolean) true)); //true
    }

    @Test
    void copyValueOf() {
        char[] charArray = "asdfqwer".toCharArray();
        System.out.println(String.copyValueOf(charArray)); //asdfqwer, String.valueOf() 와 같다
        System.out.println(String.copyValueOf(charArray, 2, 3)); //dfq, index 2 부터, 3개
    }

    @Test
    void strip() {
        System.out.println("|" + "  asdf  ".trim() + "|"); //공백 제거
        System.out.println("|" + "  asdf  ".strip() + "|"); //공백 제거
        System.out.println("|" + "  asdf  ".stripLeading() + "|"); //앞공백 제거
        System.out.println("|" + "  asdf  ".stripTrailing() + "|"); //뒤공백 제거
    }

    @Test
    void join() {
        System.out.println(String.join(",", "asdf|qwer".split("\\|"))); //asdf,qwer
    }

    @Test
    void etc() {
        "asdf\nqwer".lines().forEach(System.out::println); //lines, String 스트림 리턴
//        "asdf\nqwer".indent(2); //들여쓰기 공백 2칸 추가, java12
//        "  asdf\n  qwer".stripIndent() //들여쓰기 공백 제거, java13
    }

    @Test
    @DisplayName("String 에 없는 StringBuffer 유용한 메소드")
    void string_buffer_method() {

    }
}
