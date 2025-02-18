package dev.dk.hellojava.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JsonTest {

    /**
     * GSON
     * json object 처리
     * json array 처리
     * 객체 json 처리
     * 베열 json 처리
     *
     * 잭슨 jsonObject 부터 시작
     * 문자열 역직렬화 부터, 직렬화
     * 문자열, 샘플 객체 준비
     * 검증 코드 작성
     * root 객체 처리
     * root 배열 처리
     * root 배열 역직렬화 시 자바 타입 경고 처리
     *
     * GSON 동일 처리
     * 소스포지 json 동일 처리
     */

    @Test
    void test_ObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

    }

    String str = "{}";
    static class Data {
        int num = 10;
        String hello = "hello";
        InnerData innerData = new InnerData();
        List<Integer> numList = new ArrayList<>();
        List<String> helloList = new ArrayList<>();
        List<InnerData> innerDataList = new ArrayList<>();
    }

    static class InnerData {
        int innerNum = 20;
        String hello2 = "hello2";
    }
}
