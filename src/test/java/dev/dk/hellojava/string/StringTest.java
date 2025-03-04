package dev.dk.hellojava.string;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class StringTest {

    @SuppressWarnings({"ConstantValue", "MismatchedStringCase", "EqualsWithItself"})
    @Test
    void string_method() {
        //대소문자
        assertThat("ASDF".toLowerCase()).isEqualTo("asdf");
        assertThat("asdf".toUpperCase()).isEqualTo("ASDF");

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

        //공백 처리
        assertThat("".isEmpty()).isTrue();
        assertThat(" ".isEmpty()).isFalse();
        assertThat("".isBlank()).isTrue();
        assertThat(" ".isBlank()).isTrue();
        assertThat("asdf".trim()).isEqualTo("asdf");
        assertThat(" asdf ".trim()).isEqualTo("asdf");
        assertThat("asdf\n".trim()).isEqualTo("asdf");
        assertThat("\nasdf\n".trim()).isEqualTo("asdf");

        //탐색
        assertThat("asdf".length()).isEqualTo(4);
        assertThat("asdf".charAt(2)).isEqualTo('d');
        assertThat("asdf".indexOf((int) 'd')).isEqualTo(2);
        assertThat("asdf".indexOf("d")).isEqualTo(2);

        char c = "asdf".charAt(2);

        //todo

//
//        //탐색
//        str.length();
//        str.charAt("d");
//        str.indexOf(2);
//        str.indexOf("s");
//        str.lastIndexOf(2);
//        str.lastIndexOf("e");
//
//        //변경, 정규식
//        str.matches();
//        str.repeat();
//        str.replace();
//        str.replaceAll();
//        str.replaceFirst();
//
//        //자르기
//        str.split();
//        str.substring(1, 3);
//
//        //타입 변경
//        char[] charArray = "asdf".toCharArray();
//        byte[] bytes = "asdf".getBytes(StandardCharsets.UTF_8);
//        IntStream chars = "asdf".chars();
//        String.valueOf((int) 10);
//        String.valueOf((long) 10L);
//        String.valueOf((float) 10.2f);
//        String.valueOf((double) 10d);
//        String.valueOf((char) 'a');
//        String.valueOf(new char[]{'a','s','d','f'});
//        String.valueOf((boolean) true);

//        //복사
//        String.copyValueOf(new char[]{'a','s','d','f'});
//
//        //모르는 것들
//        str.strip();
//        str.stripLeading();
//        str.stripTrailing();
//        str.stripIndent();
    }

    /**
     * int num = ch - '0'
     */
    @Test
    @DisplayName("숫자 문자열을 숫자로 변환")
    void number_string() {
        String str = "12345";
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            int num = ch - '0';
            list.add(num);
        }

        log.info("list={}", list); //[1, 2, 3, 4, 5]
        assertThat(list).contains(1, 2, 3, 4, 5);
    }

    @Test
    @DisplayName("String 에 없는 StringBuffer 유용한 메소드")
    void string_buffer_method() {

    }
}
