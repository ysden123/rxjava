/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.rxjava.basics;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yuriy Stul
 */
public class ChainWithLogByStep {
    private static final Logger logger = LoggerFactory.getLogger(ChainWithLogByStep.class);

    /**
     * Logging after each step. Single.
     */
    private static void test1() {
        logger.info("==>test1");
        Single.just("Text 1")
                .doOnSuccess(t -> logger.info("P 1: {}", t))
                .flatMap(t -> Single.just("Text 2"))
                .doOnSuccess(t -> logger.info("P 2: {}", t))
                .subscribe(s -> logger.info("P 3: {}", s));
        logger.info("<==test1");
    }

    /**
     * Logging after each step. Observable.
     */
    private static void test2() {
        logger.info("==>test2");
        Observable.just("Text 1", "Text 2", "Text 3")
                .doOnNext(t -> logger.info("P 1: {}", t))
                .map(t -> String.format("%s Text2", t))
                .doOnNext(t -> logger.info("P 2: {}", t))
                .subscribe(s -> logger.info("P 3: {}", s));
        logger.info("<==test2");
    }

    /**
     * Logging after each step. Single. With error on step 2.
     *
     * <p>Stops after error</p>
     */
    private static void test3() {
        logger.info("==>test3");
        Single.just("Text 1")
                .doOnSuccess(t -> logger.info("P 1: {}", t))
                .flatMap(t -> Single.error(new RuntimeException("Test Error")))
                .doOnSuccess(t -> logger.info("P 2: {}", t))
                .subscribe(s -> logger.info("P 3: {}", s),
                        error -> logger.error(error.getMessage()));
        logger.info("<==test3");
    }

    /**
     * Logging after each step. Observable. With an error on step 2
     *
     * <p>Stops after error</p>
     */
    private static void test4() {
        logger.info("==>test4");
        Observable.just("Text 1", "Text 2", "Text 3")
                .doOnNext(t -> logger.info("P 1: {}", t))
                .flatMapSingle(t -> {
                    if ("Text 2".equals(t))
                        return Single.error(new RuntimeException("Test Error"));
                    else
                        return Single.just(String.format("%s Text2", t));
                })
                .doOnNext(t -> logger.info("P 2: {}", t))
                .subscribe(s -> logger.info("P 3: {}", s),
                        error -> logger.error(error.getMessage()));
        logger.info("<==test4");
    }

    public static void main(String[] args) {
        logger.info("==>main");
        test1();
        test2();
        test3();
        test4();
        logger.info("<==main");
    }
}
