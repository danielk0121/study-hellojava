package dev.dk.hellojava.httpclient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.dk.hellojava.httpclient.domain.Korean;
import dev.dk.hellojava.httpclient.domain.User;
import dev.dk.hellojava.httpclient.domain.UserListWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class RestTemplateHttpGetTest {

    /*
     * spring RestTemplate GET 요청
     *
     * 방법1 : getForObject = 지정한 '클래스 타입' 으로 변환하여 반환
     * 방법2 : getForEntity = ResponseEntity 로 반환
     * 방법3 : exchange = http 메소드를 지정하여 요청을 실행, ResponseEntity 로 반환, 요청 헤더 추가 가능
     *
     * 이슈1 : 요청 헤더, 쿼리 파라미터 추가
     * 이슈2 : POJO 객체로 응답 받기
     * 이슈3 : 응답 시, 객체 배열 문제 (json list 응답)
     * 이슈4 : response 한글 깨짐 방지, spring UriComponents 사용
     * 이슈5 : timeout 설정
     * 이슈6 : POJO 객체 응답 시, UNKNOWN_PROPERTIES 에러
     */

    @Test
    @DisplayName("getForObject() 사용")
    void run1() {
        //getForObject 사용
        RestTemplate rt = new RestTemplate();
        String url = "https://11224451-e381-4f05-aa5e-ff610e9d64ad.mock.pstmn.io/users";
        String json = rt.getForObject(url, String.class);
        System.out.println("json = " + json);
    }

    @Test
    @DisplayName("getForEntity() 사용")
    void run2() {
        //getForEntity 사용
        RestTemplate rt = new RestTemplate();
        String url = "https://11224451-e381-4f05-aa5e-ff610e9d64ad.mock.pstmn.io/users";
        ResponseEntity<String> responseEntity = rt.getForEntity(url, String.class);
        System.out.println("responseEntity = " + responseEntity);

        //responseEntity 출력, 상태코드, 헤더, 바디
        HttpStatus statusCode = responseEntity.getStatusCode();
        HttpHeaders headers = responseEntity.getHeaders();
        String body = responseEntity.getBody();
        System.out.println("statusCode = " + statusCode);
        System.out.println("headers = " + headers);
        System.out.println("body = " + body);
    }

    @Test
    @DisplayName("exchange() 사용, 요청 헤더 추가")
    void run3() {
        RestTemplate rt = new RestTemplate();

        //요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        @SuppressWarnings({"rawtypes", "unchecked"})
        HttpEntity requestEntity = new HttpEntity(headers);

        String url = "https://11224451-e381-4f05-aa5e-ff610e9d64ad.mock.pstmn.io/users/{userId}";
        String userId = "11";
        ResponseEntity<String> responseEntity = rt.exchange(
                url, HttpMethod.GET, requestEntity, String.class, userId);
        System.out.println("responseEntity = " + responseEntity);
    }

    @Test
    @DisplayName("POJO 로 응답 받기, exchange")
    void run4() {
        RestTemplate rt = new RestTemplate();

        //요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        @SuppressWarnings({"rawtypes", "unchecked"})
        HttpEntity requestEntity = new HttpEntity(headers);

        String url = "https://11224451-e381-4f05-aa5e-ff610e9d64ad.mock.pstmn.io/users/{userId}";
        String userId = "11";
        //응답받을 POJO 타입 설정
        ResponseEntity<User> responseEntity = rt.exchange(
                url, HttpMethod.GET, requestEntity, User.class, userId);
        System.out.println("responseEntity = " + responseEntity);
    }

    @Test
    @DisplayName("응답 시 객체 배열 문제 1")
    void run5_1() {
        //자바 제네릭의 타입 제거 특징 때문에 getForObject() 메소드로는 객체 목록을 직접 반환 받을 수 없다
        //[{"id":11,"name":"smith11"},{"id":12,"name":"smith12"},{"id":13,"name":"smith13"}]
        //
        //방법1 geForEntity(), 객체 배열 Object[].class 사용

        RestTemplate rt = new RestTemplate();
        String url = "https://11224451-e381-4f05-aa5e-ff610e9d64ad.mock.pstmn.io/users";

        ResponseEntity<User[]> forEntity = rt.getForEntity(url, User[].class);
        User[] body = forEntity.getBody();
        List<User> bodyList = List.of(body);
        System.out.println("bodyList = " + bodyList);
    }

    @Test
    @DisplayName("응답 시 객체 배열 문제 2")
    void run5_2() {
        //방법2 getForObject(), Wrapper 클래스 사용

        RestTemplate rt = new RestTemplate();
        String url = "https://11224451-e381-4f05-aa5e-ff610e9d64ad.mock.pstmn.io/users";

        //문제는, 서버측에서 변경해줘야 됨
        // json array 를 객체로 받으려고 하면, json parse error JsonToken.START_ARRAY 발생함
        Assertions.assertThrows(RestClientException.class, () -> {
            UserListWrapper forObject = rt.getForObject(url, UserListWrapper.class);
            List<User> users = forObject.getUsers();
            System.out.println("users = " + users);
        });
    }

    @Test
    @DisplayName("응답 시 객체 배열 문제 3")
    void run5_3() {
        //방법3 exchange(), ParameterizedTypeReference 사용

        RestTemplate rt = new RestTemplate();
        String url = "https://11224451-e381-4f05-aa5e-ff610e9d64ad.mock.pstmn.io/users";

        HttpEntity<?> requestEntity = null;
        ParameterizedTypeReference<List<User>> responseType = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<List<User>> exchange = rt.exchange(url, HttpMethod.GET, requestEntity, responseType);
        List<User> userList = exchange.getBody();
        System.out.println("userList = " + userList);
    }

    @Test
    @DisplayName("응답 시 한글 깨짐 문제 1")
    void run6_1() {
        //RestTemplate 한글 깨짐 문제는 주로 StringHttpMessageConverter 기본 문자 세트가 ISO-8859-1 설정 때문
        //방법1 : StringHttpMessageConverter 직접 추가
        //방법2 : StringHttpMessageConverter bean 설정으로 변경 => 생략
        //방법3 : 기존 StringHttpMessageConverter 제거 후 추가
        //방법4 : StringHttpMessageConverter writeAcceptCharset(false) 설정하여, Accept-Charset 헤더에 기본 문자 세트가 추가됨을 방지
        //방법5 : http 요청 헤더에 Content-Type 에 Charset 명시
        //방법6 : UriComponentsBuilder 사용하여 한글 url 파라미터를 utf8로 인코딩
        //방법7 : 톰캣 server.xml 파일에서 URI 인코딩을 UTF-8 로 설정 => 생략

        //{"id":11,"name":"스미스11"}
        String url = "https://11224451-e381-4f05-aa5e-ff610e9d64ad.mock.pstmn.io/koreans/11";

        //StringHttpMessageConverter UTF8 직접 추가
        RestTemplate rt = new RestTemplate();
        int index = 0;
        rt.getMessageConverters().add(index, new StringHttpMessageConverter(StandardCharsets.UTF_8)); //!

        ResponseEntity<Korean> exchange = rt.exchange(url, HttpMethod.GET, null, Korean.class);
        System.out.println("exchange = " + exchange);
    }

    @Test
    @DisplayName("응답 시 한글 깨짐 문제 3")
    void run6_3() {
        String url = "https://11224451-e381-4f05-aa5e-ff610e9d64ad.mock.pstmn.io/koreans/11";

        //기존 http 메세지 컨버터 제거 후 추가
        RestTemplate rt = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = rt.getMessageConverters();
        messageConverters.removeIf(converter -> converter instanceof StringHttpMessageConverter); //!
        messageConverters.add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        ResponseEntity<Korean> exchange = rt.exchange(url, HttpMethod.GET, null, Korean.class);
        System.out.println("exchange = " + exchange);
    }

    @Test
    @DisplayName("응답 시 한글 깨짐 문제 4")
    void run6_4() {
        String url = "https://11224451-e381-4f05-aa5e-ff610e9d64ad.mock.pstmn.io/koreans/11";

        //writeAcceptCharset(false), Accept-Charset 헤더에 기본 문자 세트 추가됨을 방지
        RestTemplate rt = new RestTemplate();
        StringHttpMessageConverter converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converter.setWriteAcceptCharset(false); //!
        rt.getMessageConverters().add(0, converter);

        ResponseEntity<Korean> exchange = rt.exchange(url, HttpMethod.GET, null, Korean.class);
        System.out.println("exchange = " + exchange);
    }

    @Test
    @DisplayName("응답 시 한글 깨짐 문제 5")
    void run6_5() {
        String url = "https://11224451-e381-4f05-aa5e-ff610e9d64ad.mock.pstmn.io/koreans/11";

        //http 요청 헤더에 Content-Type 에 Charset 을 명시
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Korean> exchange = rt.exchange(url, HttpMethod.GET, requestEntity, Korean.class);
        System.out.println("exchange = " + exchange);
    }

    @Test
    @DisplayName("응답 시 한글 깨짐 문제 6")
    void run6_6() {
        String url = "https://11224451-e381-4f05-aa5e-ff610e9d64ad.mock.pstmn.io/koreans/11";

        //UriComponentBuilder 사용하여 한글 url 파라미터를 utf8 로 인코딩
        UriComponentsBuilder encoded = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("key", "val11")
                .queryParam("q", "한글")
                .encode()
                ;

        //RestTemplate URL 을 String 타입으로 받는 경우, 내부적으로 URL encoding 을 수행해서 이중 인코딩이 발생 할 수 있다
        //URL 을 String 타입이 아니라 URI 객체로 전달하면 RestTemplate 내부의 자동 인코딩을 방지 할 수 있다
//        String encodedUrlString = encoded.toUriString();
        URI encodedUri = encoded.build().toUri(); //<= 이중 인코딩 방지

        RestTemplate rt = new RestTemplate();
//        ResponseEntity<Korean> exchange = rt.exchange(encodedUrlString, HttpMethod.GET, null, Korean.class);
        ResponseEntity<Korean> exchange = rt.exchange(encodedUri, HttpMethod.GET, null, Korean.class);
        System.out.println("exchange = " + exchange);
    }

    @Test
    @DisplayName("타임 아웃 설정")
    void run7() {
        //타임 아웃 설정을 위해 factory 사용
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(10); //에러 발생을 위해, 타임 아웃 짧게 설정
        factory.setReadTimeout(10);
        factory.setBufferRequestBody(false);
        RestTemplate rt = new RestTemplate(factory);

        Assertions.assertThrows(ResourceAccessException.class, () -> {
            //org.springframework.web.client.ResourceAccessException
            //org.apache.http.conn.ConnectTimeoutException
            //java.net.SocketTimeoutException
            String url = "https://11224451-e381-4f05-aa5e-ff610e9d64ad.mock.pstmn.io/koreans/11";
            ResponseEntity<Korean> exchange = rt.exchange(url, HttpMethod.GET, null, Korean.class);
            System.out.println("exchange = " + exchange);
        });
    }

    @Test
    @DisplayName("UNKNOWN_PROPERTIES 문제")
    void run8() {
        //{"id":11,"name":"스미스11","asdf":"qwer"}
        //Korean POJO 객체에 asdf 필드가 없음
        String url = "https://11224451-e381-4f05-aa5e-ff610e9d64ad.mock.pstmn.io/koreans/11";

        //FAIL_ON_UNKNOWN_PROPERTIES true 잭슨 메세지 컨버터 생성
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        MappingJackson2HttpMessageConverter failTrueJacksonConverter = new MappingJackson2HttpMessageConverter();
        failTrueJacksonConverter.setObjectMapper(objectMapper);

        //RestTemplate 에 기존 잭슨 메세지 컨버터 삭제 후 추가
        RestTemplate rt = new RestTemplate();
        List<HttpMessageConverter<?>> converters = rt.getMessageConverters();
        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter); //!
        converters.add(0, failTrueJacksonConverter);

        Assertions.assertThrows(RestClientException.class, () -> {
            //org.springframework.web.client.RestClientException
            //com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
            ResponseEntity<Korean> exchange = rt.exchange(url, HttpMethod.GET, null, Korean.class);
            System.out.println("exchange = " + exchange);
        });
    }
}
