/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.rxjava;

import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yuriy Stul
 */
public class BlockingGetWithError {
    private static final Logger logger = LoggerFactory.getLogger(BlockingGetWithError.class);

    private Single<String> success() {
        return Single.just("Success");
    }

    private Single<String> failure() {
        return Single.error(new RuntimeException("Test error"));
    }

    private void testSuccess() {
        logger.info("==>testSuccess");
        String s = null;
        try {
            s = success()
                    .doOnSuccess(logger::info)
                    .doOnError(error -> logger.error(error.getMessage()))
                    .blockingGet();
        } catch (Exception ignore) {
        }
        logger.info("s: {}", s);
        logger.info("<==testSuccess");
    }

    private void testFailure() {
        logger.info("==>testFailure");
        String s = null;
        try {
            s = failure()
                    .doOnSuccess(logger::info)
                    .doOnError(error -> logger.error(error.getMessage()))
                    .blockingGet();
        } catch (Exception ignore) {
        }
        logger.info("s: {}", s);
        logger.info("<==testFailure");
    }

    public static void main(String[] args) {
        logger.info("==>main");
        var bgwe = new BlockingGetWithError();
        bgwe.testSuccess();
        bgwe.testFailure();
        logger.info("<==main");
    }
}
