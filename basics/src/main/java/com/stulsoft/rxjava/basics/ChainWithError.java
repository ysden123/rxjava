/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.rxjava.basics;

import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yuriy Stul
 */
class ChainWithError {
    private static final Logger logger = LoggerFactory.getLogger(ChainWithError.class);

    private Single<String> error1() {
        logger.info("==>error1");
        return Single.error(new RuntimeException("Test error 1"));
    }

    private Single<String> error2() {
        logger.info("==>error2");
        return Single.error(new RuntimeException("Test error 2"));
    }

    private Single<String> success1() {
        logger.info("==>success1");
        return Single.just("Success 1");
    }

    private Single<String> success2() {
        logger.info("==>success2");
        return Single.just("Success 2");
    }

    /**
     * With error in chain
     */
    private void test1() {
        logger.info("==>test1");
        try {
            var result = success1()
//                    .doOnSuccess(s -> logger.info("success1: {}", s))
                    .flatMap(s -> error1())
//                    .doOnSuccess(s -> logger.info("error1: {}", s))
//                    .doOnError(error -> logger.error("error1 failed: {}", error.getMessage()))
                    .flatMap(s -> success2())
//                    .doOnSuccess(s -> logger.info("success2: {}", s))
//                    .doOnError(error -> logger.error("success2 failed: {}", error.getMessage()))
                    .flatMap(s -> error2())
                    .doOnSuccess(s -> logger.info("Succeeded: {}", s))
                    .doOnError(error -> logger.error("Failed: {}", error.getMessage()))
                    .blockingGet();
            logger.info("result: {}", result);
        } catch (Exception ex) {
            logger.error("General exception: {}", ex.getMessage());
        }
        logger.info("<==test1");
    }

    /**
     * Without error in chain
     */
    private void test2() {
        logger.info("==>test2");
        try {
            var result = success1()
                    .flatMap(s -> success2())
                    .doOnSuccess(s -> logger.info("Succeeded: {}", s))
                    .doOnError(error -> logger.error("Failed: {}", error.getMessage()))
                    .blockingGet();
            logger.info("result: {}", result);
        } catch (Exception ex) {
            logger.error("General exception: {}", ex.getMessage());
        }
        logger.info("<==test2");
    }

    public static void main(String[] args) {
        logger.info("==>main");
        var cwe = new ChainWithError();

        cwe.test1();
        cwe.test2();

        logger.info("<==main");
    }
}
