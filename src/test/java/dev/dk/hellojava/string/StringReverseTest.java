package dev.dk.hellojava.string;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringReverseTest {

    //todo

    @Test
    void reverse() {
        assertThat(reverse1("asdf")).isEqualTo("fdsa");
        assertThat(reverse2("asdf")).isEqualTo("fdsa");
    }

    @Test
    void reverse1_perform() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1_000_000; i++) {
            reverse1("asdf");
        }
    }

    String reverse1(String str) {
        StringBuilder ret = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            ret.append(str.charAt(i));
        }
        return ret.toString();
    }

    String reverse2(String str) {
        return new StringBuilder(str).reverse().toString();
    }
}
