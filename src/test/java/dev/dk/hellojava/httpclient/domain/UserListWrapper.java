package dev.dk.hellojava.httpclient.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class UserListWrapper {
    List<User> users = new ArrayList<>();
}
