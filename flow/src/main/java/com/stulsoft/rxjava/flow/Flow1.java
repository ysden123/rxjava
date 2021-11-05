/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.rxjava.flow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Yuriy Stul
 */
public class Flow1 {
    private static final Logger logger = LoggerFactory.getLogger(Flow1.class);

    public static void main(String[] args) {
        logger.info("==>main");
        test1();
        test2();
    }

    private static void test1() {
        logger.info("==>test1");
        flow1("Good text");
        flow1(null);
    }

    private static void test2() {
        logger.info("==>test2");
        flow2("Good text");
        flow2(null);
    }

    private static void flow1(String text1) {
        logger.info("==>flow1. text1: {}", text1);
        var result1 = new AtomicReference<String>();
        Service1.func1(text1)
                .concatMap(response -> {
                    result1.set(response);
                    return Service1.func2(response);
                })
                .concatMap(response -> Service1.func3(result1.get(), response))
                .subscribe(
                        response -> {
                            logger.info("result1: {}", result1.get());
                            logger.info("response: {}", response);
                        },
                        error -> logger.error("Failure: {}", error.getMessage())
                )
                .dispose();
    }

    private static void flow2(String text1) {
        logger.info("==>flow2. text1: {}", text1);
        var result1 = new AtomicReference<String>();
        Service1.func1(text1)
                .flatMap(response -> {
                    result1.set(response);
                    return Service1.func2(response);
                })
                .flatMap(response -> Service1.func3(result1.get(), response))
                .subscribe(
                        response -> {
                            logger.info("result1: {}", result1.get());
                            logger.info("response: {}", response);
                        },
                        error -> logger.error("Failure: {}", error.getMessage())
                )
                .dispose();
    }
}
