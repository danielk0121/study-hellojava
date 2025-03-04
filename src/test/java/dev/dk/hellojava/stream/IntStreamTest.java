package dev.dk.hellojava.stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

public class IntStreamTest {

    @Test
    void intStream_collect() {
        List<Integer> list1 = IntStream.rangeClosed(1, 4)
                .boxed()
                .collect(Collectors.toList());

        List<String> list2 = IntStream.rangeClosed(1, 4)
                .mapToObj(x -> "" + x)
                .collect(Collectors.toList());

        System.out.println("list1 = " + list1);
        System.out.println("list2 = " + list2);
        assertThat(list1).contains(1,2,3,4);
        assertThat(list2).contains("1","2","3","4");
        assertThat(list1.get(0).getClass()).isEqualTo(Integer.class);
        assertThat(list2.get(0).getClass()).isEqualTo(String.class);

        //IntStream 에 파라미터 3개짜리 collect() 사용
        ArrayList<String> list3 = IntStream.rangeClosed(1, 4)
            .collect(
                ArrayList::new,
                (ArrayList<String> list, int x) -> list.add("" + x),
                ArrayList::addAll //(ArrayList<String> x, ArrayList<String> y) -> x.addAll(y)
            );
        System.out.println("list3 = " + list3);
        assertThat(list3).contains("1","2","3","4");
    }
}
