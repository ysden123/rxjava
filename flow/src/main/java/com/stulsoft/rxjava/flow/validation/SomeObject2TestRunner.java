/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.rxjava.flow.validation;

/**
 * @author Yuriy Stul
 */
public class SomeObject2TestRunner {
    public static void main(String[] args) {
        System.out.println("==>main");
        runTest(new SomeObject2(null, null));
        runTest(new SomeObject2("Some name", null));
        runTest(new SomeObject2("Some name", 45));
        runTest(new SomeObject2("Some name", 5));
    }

    private static void runTest(SomeObject2 someObject) {
        System.out.println("==>runTest " + someObject);
        someObject.validate().ifPresent(System.out::println);
    }
}
