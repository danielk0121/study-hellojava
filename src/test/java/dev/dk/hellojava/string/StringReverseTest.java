package dev.dk.hellojava.string;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class StringReverseTest {

    @Test
    void sb_reverse() {
        //StringBuilder.reverse() 사용을 추천
        System.out.println(new StringBuilder("asdf").reverse()); //fdsa
    }

    //
    //아래는 문자열 reverse 예제들 및 테스트
    //

    @Test
    void reverse_test() {
        assertThat(reverse1("asdf")).isEqualTo("fdsa");
        assertThat(reverse2("asdf")).isEqualTo("fdsa");
        assertThat(reverse3("asdf")).isEqualTo("fdsa");
        assertThat(reverse4("asdf")).isEqualTo("fdsa");
    }

    @Test
    void reverse_perform() throws InterruptedException {
        Thread.sleep(200); //딜레이
        String str = "asdfqwer1234asdfqwer1234asdfqwer1234";

        //StringBuffer.reverse() 가 3배, 20배 빠르다
        //for           , term ms:  663
        //sb.reverse()  , term ms:  190
        //IntStream rIdx, term ms: 4455
        //String chars(), term ms: 2125
        runLoop("for           ", () -> reverse1(str));
        runLoop("sb.reverse()  ", () -> reverse2(str));
        runLoop("IntStream rIdx", () -> reverse3(str));
        runLoop("String chars()", () -> reverse4(str));
    }

    void runLoop(String name, Runnable runnable) {
        long count = 10_000_000;
        long st = System.currentTimeMillis();
        for (long i = 0; i < count; i++) {
            runnable.run();
        }
        long ed = System.currentTimeMillis();
        System.out.println(name + ", term ms: " + (ed - st));
    }

    //for 문
    String reverse1(String str) {
        StringBuilder ret = new StringBuilder();
        int len = str.length();

        for (int i = len - 1; i >= 0; i--) {
            ret.append(str.charAt(i));
        }
        return ret.toString();
    }

    //StringBuilder.reverse() 사용 => 추천
    String reverse2(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    //IntStream reverse index 사용 => for 문과 동일
    String reverse3(String str) {
        //mapToObj 때문에 boxing 비용이 발생함
        int len = str.length();
        return IntStream.range(0, len)
                .map(idx -> len - 1 - idx)
                .mapToObj(rIdx -> String.valueOf(str.charAt(rIdx)))
                .collect(Collectors.joining());
    }

    //chars() IntStream 사용 => 비효율적
    String reverse4(String str) {
        //builder offset 0 를 게속해서 생성하고 2개 쌍으로 더함 => 비효율적
        return str.chars().collect(
                StringBuilder::new, //supplier
                (builder, ch) -> builder.insert(0, (char) ch), //accumulator
                (builder1, builder2) -> builder1.insert(0, builder2) //combiner
        ).toString();
    }
}
