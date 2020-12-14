/*
 * Copyright (c) 2020. Yuriy Stul
 */

package com.stulsoft.rxjava.basics;

import io.reactivex.rxjava3.core.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Yuriy Stul
 */
public class ConcatArrayEx2 {
    private static final Logger logger = LoggerFactory.getLogger(ConcatArrayEx2.class);

    public static void main(String[] args) {
        logger.info("==>main");
        var concatArrayEx2 = new ConcatArrayEx2();
        concatArrayEx2.test1();
        logger.info("<==main");
    }

    @SuppressWarnings("unchecked")
    private void test1() {
        logger.info("==>test1");
        var counter = new CountDownLatch(1);

        Single<RunResult>[] array = new Single[]{run(sGood1()), run(sBad()), run(sGood2())};

        var goodResults=new ArrayList<RunResult>();
        var badResults=new ArrayList<RunResult>();

        Single
                .concatArray(array)
                .doOnNext(rr -> {
                    logger.info("rr: {}", rr);
                    if (rr.isValid())
                        goodResults.add(rr);
                    else
                        badResults.add(rr);
                })
                .last(new RunResult(false, "unknown"))
                .subscribe(rslt -> {

                    logger.info("Good results: {}", goodResults);
                    logger.info("Bad results: {}", badResults);

                    counter.countDown();
                });

        try {
            if (counter.await(10, TimeUnit.SECONDS))
                logger.info("Completed in time");
            else
                logger.warn("Time expired");
        } catch (Exception ignore) {
        }
        logger.info("<==test1");
    }

    private Single<RunResult> run(Single<String> s) {
        return Single.create(source -> s.subscribe(
                rslt -> source.onSuccess(new RunResult(true, rslt)),
                err -> source.onSuccess(new RunResult(false, null))
        ));
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
