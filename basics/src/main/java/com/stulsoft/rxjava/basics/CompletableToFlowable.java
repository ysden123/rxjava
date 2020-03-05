/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.rxjava.basics;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author Yuriy Stul
 */
public class CompletableToFlowable {
    private static final Logger logger = LoggerFactory.getLogger(CompletableToFlowable.class);

    private Flowable<String> someFlowable() {
        return Flowable.fromIterable(Arrays.asList("text 1", "text 2", "text 3", "text 4"));
    }

    private Completable someCompletable() {
        return Completable.complete();
    }
    private Completable someCompletableWithError() {
        return Completable.error(new RuntimeException("Test exception"));
    }

    private void test1() {
        logger.info("==>test1");
        someCompletable()
                .andThen(someFlowable())
                .subscribe(
                        logger::info,
                        error -> logger.error(error.getMessage())
                );
        logger.info("<==test1");
    }

    private void test2() {
        logger.info("==>test2");
        someCompletableWithError()
                .andThen(someFlowable())
                .subscribe(
                        logger::info,
                        error -> logger.error(error.getMessage())
                );
        logger.info("<==test2");
    }

    public static void main(String[] args) {
        logger.info("==>main");
        var c = new CompletableToFlowable();
        c.test1();
        c.test2();
        logger.info("<==main");
    }
}
