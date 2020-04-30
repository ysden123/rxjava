/*
 * Copyright (c) 2020. Yuriy Stul
 */

package com.stulsoft.rxjava.basics;

import io.reactivex.rxjava3.core.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Yuriy Stul
 */
public class ConcatEx2 {
    private static final Logger logger = LoggerFactory.getLogger(ConcatEx2.class);

    Single<String> s1() {
        return Single.timer(5, TimeUnit.SECONDS)
                .map(l -> {
                    logger.info("Inside s1");
                    return "From s1";
                });
    }

    Single<String> s2() {
        return Single.timer(1, TimeUnit.SECONDS)
                .map(l -> {
                    logger.info("Inside s2");
                    return "From s2";
                });
    }

    Single<String> longS() {
        logger.info("==>longS");
        return Single.timer(1, TimeUnit.HOURS)
                .map(l -> {
                    logger.info("Inside longS");
                    return "From longS";
                });
    }

    void test1() {
        logger.info("==>test1");
        var counter = new CountDownLatch(1);
        var result = Single
                .concat(s1(), s2())
                .last("A default value");

        result.subscribe(
                lastValue -> {
                    logger.info("last value: {}", lastValue);
                    counter.countDown();
                },
                err -> {
                    logger.error(err.getMessage(), err);
                    counter.countDown();
                }
        );

        try {
            logger.info("Before await");
            counter.await(10, TimeUnit.SECONDS);
            logger.info("After await");
        } catch (Exception err) {
            logger.error(err.getMessage(), err);
        }
        logger.info("<==test1");
    }

    void test2() {
        logger.info("==>test2");
        var counter = new CountDownLatch(1);
        var result = Single
                .concat(s1(), s2(), longS())
                .last("A default value");

        result
                .timeout(8, TimeUnit.SECONDS)
                .subscribe(
                        lastValue -> {
                            logger.info("last value: {}", lastValue);
                            counter.countDown();
                        },
                        err -> {
                            logger.error("(1) {}", err.getMessage());
                            counter.countDown();
                        }
                );

        try {
            logger.info("Before await");
            counter.await(10, TimeUnit.SECONDS);
            logger.info("After await");
        } catch (Exception err) {
            logger.error("(2) {}", err.getMessage());
        }
        logger.info("<==test2");
    }

    void test3() {
        logger.info("==>test3");
        var counter = new CountDownLatch(1);
        var result = Single
                .concat(s1(), s2(), longS())
                .last("A default value")
                .timeout(8, TimeUnit.SECONDS);

        result
                .subscribe(
                        lastValue -> {
                            logger.info("last value: {}", lastValue);
                            counter.countDown();
                        },
                        err -> {
                            logger.error("(1) {}", err.getMessage());
                            counter.countDown();
                        }
                );

        try {
            logger.info("Before await");
            counter.await(10, TimeUnit.SECONDS);
            logger.info("After await");
            logger.info("counter={}", counter.getCount());
        } catch (Exception err) {
            logger.error("(2) {}", err.getMessage());
        }
        logger.info("<==test3");
    }

    void test4() {
        logger.info("==>test4");
        var counter = new CountDownLatch(1);
        var result = Single
                .concat(s1(), s2(), longS())
                .last("A default value")
                .timeout(8, TimeUnit.SECONDS)
                .onErrorReturn(Throwable::getMessage);

        result
                .subscribe(
                        lastValue -> {
                            logger.info("last value: {}", lastValue);
                            counter.countDown();
                        },
                        err -> {
                            logger.error("(1) {}", err.getMessage());
                            counter.countDown();
                        }
                );

        try {
            logger.info("Before await");
            counter.await(10, TimeUnit.SECONDS);
            logger.info("After await");
            logger.info("counter={}", counter.getCount());
        } catch (Exception err) {
            logger.error("(2) {}", err.getMessage());
        }
        logger.info("<==test4");
    }

    public static void main(String[] args) {
        logger.info("==>main");
        var ce = new ConcatEx2();
        ce.test1();
        ce.test2();
        ce.test3();
        ce.test4();
        logger.info("<==main");
    }
}
