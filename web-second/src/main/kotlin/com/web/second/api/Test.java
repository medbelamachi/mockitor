package com.web.second.api;

import java.util.stream.IntStream;

public class Test {
    public static void main(String[] args) {
        System.out.println(IntStream.range(0, 11).filter(i -> i % 3 == 0 || i % 3 == 0 || i % 3 == 0).sum());
        ;
    }
}
