package dev.dk.hellojava.httpclient;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RestTemplateHttpPostTest {

    /*
    spring RestTemplate POST 요청

    방법1 : postForObject() = 요청 후, 응답 본문을 지정된 클래스 유형으로 반환
    방법2 : postForEntity() = 요청 후, 응답을 ResponseEntity 로 반환
    방법3 : postForLocation() = 요청 후, Location 헤더 값을 반환 => 생략
    방법4 : exchange() = 헤더 포함 요청 후, ResponseEntity 로 반환

    ---
    spring RestTemplate PUT, DELETE 요청
    방법1 : exchange() 사용
     */

    @Test
    @DisplayName("postForObject 사용")
    void run1() {
        RestTemplate rt = new RestTemplate();
        String url = "https://11224451-e381-4f05-aa5e-ff610e9d64ad.mock.pstmn.io/users";
        Object requestBody = "request body";
        String json = rt.postForObject(url, requestBody, String.class);
        System.out.println("json = " + json);
    }

    @Test
    @DisplayName("postForEntity 사용")
    void run2() {
        RestTemplate rt = new RestTemplate();
        String url = "https://11224451-e381-4f05-aa5e-ff610e9d64ad.mock.pstmn.io/users";
        Object requestBody = "request body";
        ResponseEntity<String> responseEntity = rt.postForEntity(url, requestBody, String.class);
        System.out.println("responseEntity = " + responseEntity);
    }

    @Test
    @DisplayName("exchange 사용")
    void run3() {
        RestTemplate rt = new RestTemplate();
        String url = "https://11224451-e381-4f05-aa5e-ff610e9d64ad.mock.pstmn.io/users";

        Map<String, Object> requestBody = new LinkedHashMap<>();
        requestBody.put("message", "sample message");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, requestHeaders);

        ResponseEntity<String> responseEntity = rt.exchange(
                url, HttpMethod.POST, requestEntity, String.class);
        System.out.println("responseEntity = " + responseEntity);
    }

    @Test
    @DisplayName("PUT exchange 사용")
    void run4() {
        RestTemplate rt = new RestTemplate();
        String url = "https://11224451-e381-4f05-aa5e-ff610e9d64ad.mock.pstmn.io/users/{userId}";
        Long userId = 123L;

        Map<String, Object> requestBody = new LinkedHashMap<>();
        requestBody.put("message", "sample message");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, requestHeaders);

        //DELETE 요청도 같으므로 생략
//        ResponseEntity<String> responseEntity = rt.exchange(
//                url, HttpMethod.PUT, requestEntity, String.class, userId);
        ResponseEntity<String> responseEntity = rt.exchange(
                url, HttpMethod.DELETE, requestEntity, String.class, userId);
        System.out.println("responseEntity = " + responseEntity);
    }

}
