package dev.dk.hellojava.string;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringRandomTest {

    @Test
    @DisplayName("랜덤 문자열 생성, nextInt() 사용")
    void random_char_nextInt() {
        Random rand = new Random();
        int numberOfAz = 'z' - 'a' + 1; //26

        char uc = (char) (rand.nextInt(numberOfAz) + 'A'); //랜덤 대문자
        char lc = (char) (rand.nextInt(numberOfAz) + 'a'); //랜덤 대문자
        int rInt = rand.nextInt(10); //랜덤 숫자 [0,9]

        System.out.println("upperChar = " + uc);
        System.out.println("lowerChar = " + lc);
        System.out.println("intValue = " + rInt);

        //랜덤 숫자 10개 리스트
        List<Integer> list = IntStream.rangeClosed(1, 10).boxed()
                .map(i -> rand.nextInt(10))
                .collect(Collectors.toList());
        System.out.println("list = " + list);
    }

    @Test
    @DisplayName("랜덤 문자열 생성, nextInt(), 문자열 하드코딩 배열 사용")
    void random_char_array() {
        Random rand = new Random();
        String str1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //len=26
        String str2 = "abcdefghijklmnopqrstuvwxyz"; //len=26
        String str3 = "0123456789"; //len=10

        String str = str1 + str2 + str3;
        int len = 26 + 26 + 10;
        char ch = str.charAt(rand.nextInt(len));
        System.out.println("ch = " + ch);

        //길이 10 랜덤 문자열
        String randString = IntStream.rangeClosed(1, 10).boxed()
                .map(i -> "" + str.charAt(rand.nextInt(len)))
                .collect(Collectors.joining());
        System.out.println("randString = " + randString);
    }
}
