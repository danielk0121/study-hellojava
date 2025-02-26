package dev.dk.hellojava.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class JsonTest {

    /**
     * root 가 객체인 json 처리
     *   객체 to json 문자열
     *   json 문자열 to 객체
     *
     * root 가 배열인 json 처리
     *   배열 to json 문자열
     *   json 문자열 to 배열
     *
     * 1. 잭슨 jsonObject 사용
     * 2. GSON 사용
     * 3. 소스포지 json 사용 => 다음에
     *
     * 주의사항
     * 검증 코드 작성
     * root 배열 역직렬화 시 자바 타입 경고 처리
     */

    @Test
    void test_Gson_object() {
        //객체 to json 문자열, 반대로
        //
        //요약
        //Gson gson = new Gson()
        //jsonString = gson.toJson(data)
        //data = gson.fromJson(rawString, Data.class) //Class 사용
        //data = gson.fromJson(rawString, new TypeToken<Data>(){}.getType()) //제네릭 타입 명시
        //data = gson.fromJson(rawString, new TypeToken<>(){}) //제네릭 타입 생략

        Gson gson = new Gson();
        Data data = createSampleData();
        String rawString = "{\"num\":10,\"str\":\"dataHello\",\"innerData\":{\"id\":1,\"innerNum\":10,\"innerStr\":\"innerDataHello\"},\"numList\":[10,20],\"strList\":[\"a1\",\"a2\"],\"innerDataList\":[{\"id\":2,\"innerNum\":20,\"innerStr\":\"bb2\"}]}";

        String jsonString = gson.toJson(data);
        log.info("jsonString={}", jsonString);

        Data readData1 = gson.fromJson(rawString, Data.class);
        Data readData2 = gson.fromJson(rawString, new TypeToken<Data>() {});
        Data readData3 = gson.fromJson(rawString, new TypeToken<Data>() {}.getType());
        log.info("readData1={}", readData1);
        log.info("readData2={}", readData2);
        log.info("readData3={}", readData3);
    }

    @Test
    void test_Gson_array() {
        //배열 to json 문자열, 반대로
        //
        //요약
        //Gson gson = new Gson()
        //jsonString = gson.toJson(data)
        //list = gson.fromJson(arrayString, new TypeToken<>(){}.getType())

        Gson gson = new Gson();
        List<String> data = Arrays.asList("a1", "a2", "a3");
        String arrayString = "[\"a1\", \"a2\", \"a3\"]";

        String jsonString = gson.toJson(data);
        log.info("jsonString={}", jsonString);

        //1. TypeToken 에 T 가 인텔리에서 회색 처리됨
        List<String> list1 = gson.fromJson(arrayString, new TypeToken<List<String>>() {});

        //2. new TypeToken<>(){}.getType() 을 사용하여, 제네릭 타입이 생략되는 것을 방지
        Type listType = new TypeToken<List<String>>() {}.getType();
        List<String> list2 = gson.fromJson(arrayString, listType);

        //3. 2번을 합쳐서 사용
        List<String> list3 = gson.fromJson(arrayString, new TypeToken<List<String>>() {}.getType());

        log.info("list1={}", list1);
        log.info("list2={}", list2);
        log.info("list3={}", list3);
    }

    @Test
    void test_ObjectMapper_object() throws JsonProcessingException {
        //객체 to json 문자열
        //반대로

        ObjectMapper objectMapper = new ObjectMapper();
        Data data = createSampleData();
        String rawString = "{\"num\":10,\"str\":\"dataHello\",\"innerData\":{\"id\":1,\"innerNum\":10,\"innerStr\":\"innerDataHello\"},\"numList\":[10,20],\"strList\":[\"a1\",\"a2\"],\"innerDataList\":[{\"id\":2,\"innerNum\":20,\"innerStr\":\"bb2\"}]}";

        //객체 to json string
        String jsonString = objectMapper.writeValueAsString(data);
        log.info("jsonStr={}", jsonString);

        //json string to 객체
        Data readData1 = objectMapper.readValue(rawString, Data.class);
        Data readData2 = objectMapper.readValue(rawString, new TypeReference<Data>() { });
        Data readData3 = objectMapper.readValue(rawString, new TypeReference<>() { });
        log.info("readData1={}", readData1);
        log.info("readData2={}", readData2);
        log.info("readData3={}", readData3);

        //에러 모음
        //
        //ObjectMapper => SerializationFeature.FAIL_ON_EMPTY_BEANS => getter 가 없을 경우 에러
    }

    @Test
    void test_ObjectMapper_array() throws JsonProcessingException {
        //배열 to json 문자열
        //반대로

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> data = Arrays.asList("a1", "a2", "a3");
        String arrayString = "[\"a1\", \"a2\", \"a3\"]";

        String jsonString = objectMapper.writeValueAsString(data);
        log.info("jsonString={}", jsonString);

        //제네릭 타입 명시 안하면 경고 뜸
        @SuppressWarnings("unchecked")
        List<String> readData1 = objectMapper.readValue(arrayString, List.class);
        @SuppressWarnings({"unchecked", "rawtypes"})
        List<String> readData2 = objectMapper.readValue(arrayString, new TypeReference<List>() {});

        //제네릭 타입을 전부다 명시 하거나, 아예 생략하면 됨, 자바8 까지는 전부 명시, 11부터 생략 가능
        List<String> readData3 = objectMapper.readValue(arrayString, new TypeReference<List<String>>() {});
        List<String> readData4 = objectMapper.readValue(arrayString, new TypeReference<>() {
        });
        log.info("readData1={}", readData1);
        log.info("readData2={}", readData2);
        log.info("readData3={}", readData3);
        log.info("readData4={}", readData4);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
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
    @NoArgsConstructor
    @ToString
    static class InnerData {
        long id;
        int innerNum;
        String innerStr;
        public InnerData(long id, int innerNum, String innerStr) {
            this.id = id;
            this.innerNum = innerNum;
            this.innerStr = innerStr;
        }
    }

    private Data createSampleData() {
        Data data = new Data();
        data.setNum(10);
        data.setStr("dataHello");
        data.setInnerData(new InnerData(1L, 10, "innerDataHello"));
        data.setNumList(Arrays.asList(10, 20));
        data.setStrList(Arrays.asList("a1", "a2"));
        data.setInnerDataList(List.of(
                new InnerData(2L, 20, "bb2")));
        return data;
    }
}
