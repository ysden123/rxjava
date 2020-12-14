/*
 * Copyright (c) 2020. Yuriy Stul
 */

package com.stulsoft.rxjava.basics;

import io.reactivex.rxjava3.core.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Yuriy Stul
 */
public class ConcatArrayEx1 {
    private static final Logger logger = LoggerFactory.getLogger(ConcatArrayEx1.class);

    public static void main(String[] args) {
        logger.info("==>main");
        var concatArrayEx1 = new ConcatArrayEx1();

        concatArrayEx1.test1();
        concatArrayEx1.test2();
        concatArrayEx1.test3();
    }

    @SuppressWarnings("unchecked")
    void test1() {
        logger.info("==>test1");
        var counter = new CountDownLatch(1);
        Single<String>[] array = new Single[]{sGood1(), sGood2()};
        var result = Single.concatArray(array);

        result
                .doOnComplete(counter::countDown)
                .subscribe(
                        s -> {
                            logger.info("s: {}", s);
                        },
                        err -> {
                            logger.error("err: {}", err.getMessage());
                            counter.countDown();
                        }
                );

        try {
            counter.await(10, TimeUnit.SECONDS);
        } catch (Exception ignore) {
        }
        logger.info("<==test1");
    }

    @SuppressWarnings("unchecked")
    void test2() {
        logger.info("==>test2");
        var counter = new CountDownLatch(1);
        Single<String>[] array = new Single[]{sGood1(), sBad(), sGood2()};
        var result = Single.concatArray(array);

        result
                .doOnComplete(counter::countDown)
                .subscribe(
                        s -> {
                            logger.info("s: {}", s);
                        },
                        err -> {
                            logger.error("err: {}", err.getMessage());
                            counter.countDown();
                        }
                );

        try {
            counter.await(10, TimeUnit.SECONDS);
        } catch (Exception ignore) {
        }
        logger.info("<==test2");
    }

    @SuppressWarnings("unchecked")
    void test3() {
        logger.info("==>test3");
        var counter = new CountDownLatch(1);

        var list = Arrays.asList(sGood1(), sBad(), sGood2());

        var rrr = Single.zip(list, ttt -> ttt.length);
        rrr.subscribe(
                l -> {
                    logger.info("l={}", l);
                    counter.countDown();
                },
                err -> {
                    logger.error("err: {}", err.getMessage());
                    counter.countDown();
                }
        );

        try {
            counter.await(10, TimeUnit.SECONDS);
        } catch (Exception ignore) {
        }
        logger.info("<==test3");
    }

    Single<String> sGood1() {
        return Single.timer(1, TimeUnit.SECONDS)
                .map(l -> {
                    logger.info("==>sGood1");
                    return "From sGood1";
                });
    }

    Single<String> sGood2() {
        return Single.timer(1, TimeUnit.SECONDS)
                .map(l -> {
                    logger.info("==>sGood2");
                    return "From sGood2";
                });
    }

    Single<String> sBad() {
        return Single.create(source -> {
            Single.timer(1, TimeUnit.SECONDS)
                    .subscribe(l -> source.onError(new RuntimeException("error in sBad")));
        });
    }
}
