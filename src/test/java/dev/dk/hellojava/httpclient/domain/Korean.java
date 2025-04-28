package dev.dk.hellojava.httpclient.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Korean {
    Long id;
    String name;
    //String asdf; //FAIL_ON_UNKNOWN_PROPERTIES 테스트 목적
}
