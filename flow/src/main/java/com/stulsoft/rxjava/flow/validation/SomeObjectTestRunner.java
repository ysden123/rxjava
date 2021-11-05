/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.rxjava.flow.validation;

/**
 * @author Yuriy Stul
 */
public class SomeObjectTestRunner {
    public static void main(String[] args) {
        System.out.println("==>main");
        runTest(new SomeObject(null, null));
        runTest(new SomeObject("Some name", null));
        runTest(new SomeObject("Some name", 45));
        runTest(new SomeObject("Some name", 5));
    }

    private static void runTest(SomeObject someObject) {
        System.out.println("==>runTest " + someObject);
        someObject.validate().ifPresent(System.out::println);
    }
}
