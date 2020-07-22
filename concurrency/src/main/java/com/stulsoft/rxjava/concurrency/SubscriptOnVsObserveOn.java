/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.rxjava.concurrency;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Playing with variants of usage the subscribeOn and observeOn.
 *
 * <p>If consumer takes long time then the fastest variant is <strong><i>subscribeOn</i></strong>.</p>
 * <p>If consumer doesn't takes long time then it is never mind which variant to use.</p>
 *
 * @author Yuriy Stul
 */
public class SubscriptOnVsObserveOn {
    private static final Logger logger = LoggerFactory.getLogger(SubscriptOnVsObserveOn.class);

    private Flowable<String> longGenerator(int size, long interval) {
        return Flowable.create(emitter -> {
            for (int i = 1; i <= size; ++i) {
                try {
                    Thread.sleep(interval);
                } catch (Exception ignore) {
                }
                var text = "Data " + i;
                emitter.onNext(text);
            }
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);
    }

    private void longConsumer(long interval) {
        try {
            Thread.sleep(interval);
        } catch (Exception ignore) {
        }

//        logger.debug(text);
    }

    private void shortConsumer(final String text) {
    }

    private void withoutConcurrency(int size, long interval) {
        var start = System.currentTimeMillis();
        longGenerator(size, interval)
                .blockingSubscribe(item -> longConsumer(interval));
        logger.info("withoutConcurrency:          duration = {} ms", System.currentTimeMillis() - start);
    }

    private void withoutConcurrencyS(int size, long interval) {
        var start = System.currentTimeMillis();
        longGenerator(size, interval)
                .blockingSubscribe(this::shortConsumer);
        logger.info("withoutConcurrencyS:          duration = {} ms", System.currentTimeMillis() - start);
    }

    private void withSubscribeOn(int size, long interval) {
        var start = System.currentTimeMillis();
        longGenerator(size, interval)
                .subscribeOn(Schedulers.io())
                .blockingSubscribe(item -> longConsumer(interval));
        logger.info("withSubscribeOn:             duration = {} ms", System.currentTimeMillis() - start);
    }

    private void withSubscribeOnS(int size, long interval) {
        var start = System.currentTimeMillis();
        longGenerator(size, interval)
                .subscribeOn(Schedulers.io())
                .blockingSubscribe(this::shortConsumer);
        logger.info("withSubscribeOnS:             duration = {} ms", System.currentTimeMillis() - start);
    }

    private void withObserveOn(int size, long interval) {
        var start = System.currentTimeMillis();
        longGenerator(size, interval)
                .observeOn(Schedulers.io())
                .blockingSubscribe(item -> longConsumer(interval));
        logger.info("withObserveOn:               duration = {} ms", System.currentTimeMillis() - start);
    }

    private void withObserveOnS(int size, long interval) {
        var start = System.currentTimeMillis();
        longGenerator(size, interval)
                .observeOn(Schedulers.io())
                .blockingSubscribe(this::shortConsumer);
        logger.info("withObserveOnS:               duration = {} ms", System.currentTimeMillis() - start);
    }

    private void withSubscribeOnAndObserveOn(int size, long interval) {
        var start = System.currentTimeMillis();
        longGenerator(size, interval)
                .observeOn(Schedulers.io())
                .blockingSubscribe(item -> longConsumer(interval));
        logger.info("withSubscribeOnAndObserveOn: duration = {} ms", System.currentTimeMillis() - start);
    }

    private void withSubscribeOnAndObserveOnS(int size, long interval) {
        var start = System.currentTimeMillis();
        longGenerator(size, interval)
                .observeOn(Schedulers.io())
                .blockingSubscribe(this::shortConsumer);
        logger.info("withSubscribeOnAndObserveOnS: duration = {} ms", System.currentTimeMillis() - start);
    }

    private void useLongConsumer(int size, long interval) {
        logger.info("==>longConsumer");
        withoutConcurrency(size, interval);
        withSubscribeOn(size, interval);
        withObserveOn(size, interval);
        withSubscribeOnAndObserveOn(size, interval);
    }

    private void useShortConsumer(int size, long interval) {
        logger.info("==>shortConsumer");
        withoutConcurrencyS(size, interval);
        withSubscribeOnS(size, interval);
        withObserveOnS(size, interval);
        withSubscribeOnAndObserveOnS(size, interval);
    }

    public static void main(String[] args) {
        var so = new SubscriptOnVsObserveOn();
        var size = 10;
        var interval = 1000;
        so.useLongConsumer(size, interval);
        so.useShortConsumer(size, interval);
    }
}
