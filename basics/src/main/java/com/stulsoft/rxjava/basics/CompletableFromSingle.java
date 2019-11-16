/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.rxjava.basics;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yuriy Stul
 */
public class CompletableFromSingle {
    private static final Logger logger = LoggerFactory.getLogger(CompletableFromSingle.class);

    private Disposable dis;

    public static void main(String[] args) {
        logger.info("==>main");

        var c = new CompletableFromSingle();
        c.testSuccess();
        c.testFailure();

        logger.info("<==main");
    }

    private void testSuccess() {
        logger.info("==>testSuccess");
        test(successSingle());
        dis.dispose();
        logger.info("<==testSuccess");
    }

    private void testFailure() {
        logger.info("==>testFailure");
        test(failedSingle());
        dis.dispose();
        logger.info("<==testFailure");
    }

    private void test(Single<String> single) {
        dis = buildCompletable(single)
                .subscribe(() -> logger.info("Success"),
                        error -> logger.error("Failed: {}", error.getMessage()));
    }

    private Single<String> successSingle() {
        return Single.just("Successful single");
    }

    private Single<String> failedSingle() {
        return Single.error(new RuntimeException("Cannot create single (test!)"));
    }

    private Completable buildCompletable(Single<String> single) {
        return Completable.fromSingle(single);
    }
}
