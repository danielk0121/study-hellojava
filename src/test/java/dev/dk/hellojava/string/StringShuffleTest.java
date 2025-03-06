package dev.dk.hellojava.string;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class StringShuffleTest {

    @Test
    void letter_shuffle_problem() {
        //주어지는 입력
        String str = "asdf"; //문자열
        int[] idxArray = new int[]{ 2, 0, 3, 1 }; //문자 각각 인덱스를 기록한 배열
        //출력이 s f a d 순서로 나와야 됨

        //입력 문자열과 동일한 길이의 배열을 생성해서 처리함
        char[] chars = str.toCharArray();
        char[] newChars = new char[chars.length];

        for (int i = 0; i < idxArray.length; i++) {
            char tCh = str.charAt(i);
            int tIdx = idxArray[i];
            newChars[tIdx] = tCh;
            System.out.println("i, tIdx, tCh = " + i + ", " + tIdx + ", " + tCh);
        }

        //출력
        String out = String.valueOf(newChars);
        System.out.println("out = " + out); //sfad
    }

    @Test
    void string_shuffle_collections_shuffle() {
        String str = "asdfqwer";
        List<String> letterList = Arrays.asList(str.split(""));
        List<String> copyList = new ArrayList<>(letterList);
        Collections.shuffle(copyList);
        String out = String.join("", copyList);
        System.out.println("str = " + str);
        System.out.println("out = " + out);

        assertThat(out.length()).isEqualTo(str.length());
        assertThat(out.split("")).containsAll(letterList);
        System.out.println("letterList  = " + letterList);
        System.out.println("out.split() = " + Arrays.asList(out.split("")));
    }
}
