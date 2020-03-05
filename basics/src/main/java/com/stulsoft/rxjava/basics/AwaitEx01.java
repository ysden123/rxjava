/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.rxjava.basics;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Playing with await
 *
 * @author Yuriy Stul
 */
public class AwaitEx01 {
    private static final Logger logger = LoggerFactory.getLogger(AwaitEx01.class);

    private Single<String> timeout1() {
        return Single.create(observer -> {
            try {
                Thread.sleep(1000);
                observer.onSuccess("tttt");
            } catch (Exception ignore) {
            }
        });
    }

    private void test1() {
        logger.info("==>test1");
        var observer = new TestObserver<String>();

        var timeout1Result = timeout1();
        timeout1Result
                .doOnSuccess(logger::info)
                .subscribeOn(Schedulers.io())
                .subscribe(observer);

        try {
            logger.info("before await...");
            observer.await(300, TimeUnit.MILLISECONDS);
            logger.info("after await...");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        observer.assertComplete();

        logger.info("<==test1");
    }

    private void test2() {
        logger.info("==>test2");
        var observer = new TestObserver<String>();
        var timeout1Result = timeout1();

        timeout1Result
                .subscribeOn(Schedulers.io())
                .subscribe(observer);

        try {
            observer.awaitDone(300, TimeUnit.MILLISECONDS);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        observer.assertComplete();

        logger.info("<==test2");
    }

    public static void main(String[] args) {
        logger.info("==>main");
        var c = new AwaitEx01();
        c.test1();
        c.test2();
        logger.info("<==main");
    }
}
