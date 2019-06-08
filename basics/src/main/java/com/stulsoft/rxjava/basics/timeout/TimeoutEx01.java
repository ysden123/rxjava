/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.rxjava.basics.timeout;

import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Usage of <i>timeout</i> operator
 *
 * @author Yuriy Stul
 */
public class TimeoutEx01 {
    private static final Logger logger = LoggerFactory.getLogger(TimeoutEx01.class);

    /**
     * No timeout
     */
    private static void test1() {
        logger.info("==>test1");

        generate(100)
                .subscribe(result -> logger.info(result),
                        err -> logger.error(err.getMessage()));

        logger.info("<==test1");
    }

    /**
     * With timeout, but without blockingGet
     */
    private static void test2() {
        logger.info("==>test2");

        generate(500)
                .timeout(100, TimeUnit.MILLISECONDS)
                .subscribe(result -> logger.info(result),
                        err -> logger.error(err.getMessage()));

        logger.info("<==test2");
    }

    /**
     * Timeout and blocking get. Missing right exception handling.
     */
    private static void test3() {
        logger.info("==>test3");

        generate(500)
                .timeout(100, TimeUnit.MILLISECONDS)
                .doOnSuccess(result -> logger.info(result))
                .doOnError(err -> logger.error(err.getMessage()))
                .blockingGet();

        logger.info("<==test3");
    }

    /**
     * Timeout and blocking get. Right exception handling
     */
    private static void test4_1() {
        logger.info("==>test4_1");

        try {
            generate(500)
                    .timeout(700, TimeUnit.MILLISECONDS)
                    .doOnSuccess(result -> logger.info(result))
                    .doOnError(err -> logger.error(err.getMessage()))
                    .blockingGet();
        } catch (Exception ex) {
            logger.error("(2) {}", ex.getMessage());
        }

        logger.info("<==test4_1");
    }

    /**
     * Timeout and blocking get. Right exception handling
     */
    private static void test4_2() {
        logger.info("==>test4_2");

        try {
            generate(500)
                    .timeout(100, TimeUnit.MILLISECONDS)
                    .doOnSuccess(result -> logger.info(result))
                    .doOnError(err -> logger.error(err.getMessage()))
                    .blockingGet();
        } catch (Exception ex) {
            logger.error("(2) {}", ex.getMessage());
        }

        logger.info("<==test4_2");
    }

    private static Single<String> generate(int pause) {
        return Single.fromCallable(() -> {
            try {
                Thread.sleep(pause);
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            }
            return "Generated " + pause;
        });
    }

    public static void main(String[] args) {
        System.out.println("==>main");
        test1();
        test2();
        try {
            test3();
        } catch (Exception ex) {
            logger.error("(M) {}", ex.getMessage());
        }
        test4_1();
        test4_2();
        logger.info("<==main");
    }
}
