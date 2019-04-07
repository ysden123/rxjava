/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.rxjava.multiplicator;

import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yuriy Stul
 */
public class ManySubscription {
    private static final Logger logger = LoggerFactory.getLogger(ManySubscription.class);

    public static void main(String[] args) {
        logger.info("==>main");
        test1();
        test2();
        logger.info("<==main");
    }

    /**
     * Generator is working few times - for each subscriber
     */
    private static void test1() {
        logger.info("==>test1");

        var observable = generate(2);

        observable.subscribe(i -> logger.info("Subscriber 1: {}", i));
        observable.subscribe(i -> logger.info("Subscriber 2: {}", i));
        logger.info("<==test1");
    }

    /**
     * Generator is working one time - for all subscribers
     */
    private static void test2() {
        logger.info("==>test2");

        var observable = generate(2);

        observable
                .doOnNext(i -> logger.info("Consumer 1: {}", i))
                .doOnNext(i -> logger.info("Consumer 2: {}", i))
                .subscribe();

        logger.info("<==test2");
    }

    private static Observable<Integer> generate(int n) {
        return Observable.create(observer -> {
            for (int i = 1; i <= n; ++i) {
                logger.info("Generate item {}", i);
                observer.onNext(i);
            }
        });
    }
}
