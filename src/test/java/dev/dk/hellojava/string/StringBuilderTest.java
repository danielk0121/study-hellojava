package dev.dk.hellojava.string;

import org.junit.jupiter.api.Test;

public class StringBuilderTest {

    //todo 정리
    //String 과 차이점이나 StringBuilder 유용성에 대해 추가적으로 구글링 및 정리 필요

    @Test
    void foo() {
        //StringBuilder : 문자열을 동적으로 생성하고 조작할 때 사용함
        // String이랑 달리 내부 버퍼를 가지고 있기 때문에
        // 문자열 조작 시, 새로운 문자열 객체를 생성하지 않음


        StringBuilder sb = new StringBuilder("hello"); // sb = "hello"
        sb.append(" world"); // sb = "hello world"
        sb.insert(0, "say "); // sb = "say hello world"
        sb.delete(0, 4); // sb = "hello world"
        sb.replace(0, 2, "Hi"); // sb = "Hillo world"
        sb.reverse(); // sb = "dlrow olliH"
        String result = sb.toString(); // result = "dlrow olliH"
        sb.append(("c" + "").repeat(4));//sb에 문자열 c를 n번 반복해서 넣음
        //sb = "dlrow olliHcccc"

        //todo


    }
}
