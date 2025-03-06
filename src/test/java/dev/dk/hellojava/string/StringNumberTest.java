package dev.dk.hellojava.string;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class StringNumberTest {

    /**
     * int num = ch - '0'
     */
    @Test
    @DisplayName("숫자 문자열을 숫자 배열로 변환")
    void number_string() {
        String str = "012345";

        //str.chars() 사용
        List<Integer> list = str.chars() //chars => IntStream
                .map(intVal -> ((char) intVal) - '0')
                .boxed().collect(Collectors.toList());

        System.out.println(list.getClass()); //java.util.ArrayList
        System.out.println("list = " + list); //[0, 1, 2, 3, 4, 5]
        assertThat(list).containsExactly(0, 1, 2, 3, 4, 5);

        //str.charAt(i) 사용
        List<Integer> list2 = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            int num = ch - '0';
            list2.add(num);
        }
        assertThat(list).containsExactly(0, 1, 2, 3, 4, 5);
    }
}
