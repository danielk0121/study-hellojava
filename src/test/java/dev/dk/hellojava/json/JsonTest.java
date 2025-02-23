package dev.dk.hellojava.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class JsonTest {

    /**
     * json object to string
     * json string to object
     * json array to string
     * json string to array
     *
     * 잭슨 jsonObject 부터 시작
     * 문자열 역직렬화 부터, 직렬화
     * 문자열, 샘플 객체 준비
     *
     * 검증 코드 작성
     * root 객체 처리
     * root 배열 처리
     * root 배열 역직렬화 시 자바 타입 경고 처리
     *
     * GSON 동일 처리
     * 소스포지 json 동일 처리
     */

    @Test
    void test_ObjectMapper() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Data data = createNewData();

        //object with json
        //
        //SerializationFeature.FAIL_ON_EMPTY_BEANS => getter 가 없는 에러
        //
        //{"num":10,"str":"hello","innerData":{"id":0,"innerNum":20,"innerStr":"innerHello"},"numList":[10,20,30],"strList":["a1","a2","a3"],"innerDataList":[{"id":1,"innerNum":1,"innerStr":null},{"id":2,"innerNum":2,"innerStr":null},{"id":3,"innerNum":3,"innerStr":null}]}
        String jsonStr = objectMapper.writeValueAsString(data);
        log.info("jsonStr={}", jsonStr);

        //array with json
        //
        //new TypeReference<>() 사용
        //
        //jsonArrayStr=["a1","a2","a3"]
        List<String> stringArray = Arrays.asList("a1","a2","a3");
        String jsonArrayStr = objectMapper.writeValueAsString(stringArray);
        log.info("jsonArrayStr={}", jsonArrayStr);

        String str2 = "[\"a1\", \"a2\", \"a3\"]";
        List<String> str2List = objectMapper.readValue(
                str2, new TypeReference<>() {
                });
        log.info("str2    ={}", str2);
        log.info("str2List={}", str2List);
    }

    @Getter
    @Setter
    static class Data {
        int num;
        String str;
        InnerData innerData = new InnerData();
        List<Integer> numList = new ArrayList<>();
        List<String> strList = new ArrayList<>();
        List<InnerData> innerDataList = new ArrayList<>();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    static class InnerData {
        long id;
        int innerNum;
        String innerStr;
    }

    String str = "{\n" +
            "    \"num\": 10,\n" +
            "    \"str\": \"hello\",\n" +
            "    \"innerData\": {\n" +
            "        \"id\": 0,\n" +
            "        \"innerNum\": 20,\n" +
            "        \"innerStr\": \"innerHello\"\n" +
            "    },\n" +
            "    \"numList\": [\n" +
            "        10,\n" +
            "        20,\n" +
            "        30\n" +
            "    ],\n" +
            "    \"strList\": [\n" +
            "        \"a1\",\n" +
            "        \"a2\",\n" +
            "        \"a3\"\n" +
            "    ],\n" +
            "    \"innerDataList\": [\n" +
            "        {\n" +
            "            \"id\": 1,\n" +
            "            \"innerNum\": 1,\n" +
            "            \"innerStr\": \"b1\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 2,\n" +
            "            \"innerNum\": 2,\n" +
            "            \"innerStr\": \"b2\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 3,\n" +
            "            \"innerNum\": 3,\n" +
            "            \"innerStr\": \"b3\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    private Data createNewData() {
        InnerData innerData = new InnerData();
        innerData.setId(0L);
        innerData.setInnerNum(20);
        innerData.setInnerStr("innerHello");

        Data data = new Data();
        data.setNum(10);
        data.setStr("hello");
        data.setInnerData(innerData);
        data.setNumList(Arrays.asList(10,20,30));
        data.setStrList(Arrays.asList("a1","a2","a3"));
        data.setInnerDataList(Arrays.asList(
                new InnerData(1L,1,"b1"),
                new InnerData(2L,2,"b2"),
                new InnerData(3L,3,"b3")
                ));
        return data;
    }
}
