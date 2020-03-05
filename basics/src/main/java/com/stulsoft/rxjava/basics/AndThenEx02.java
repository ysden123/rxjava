/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.rxjava.basics;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demonstrates usage the <i>andThen</i> with <i>defer</i>
 *
 * @author Yuriy Stul
 */
public class AndThenEx02 {
    private static final Logger logger = LoggerFactory.getLogger(AndThenEx02.class);


    private void testBad() {
        logger.info("==>testBad");
        subscribe()
                .subscribe(
                        item -> logger.info("item: {}", item),
                        error -> logger.error(error.getMessage())
                );
        logger.info("<==testBad");
    }

    private void test1() {
        logger.info("==>test1");
        subscribe1()
                .subscribe(
                        item -> logger.info("item: {}", item),
                        error -> logger.error(error.getMessage())
                );
        logger.info("<==test1");
    }

    private void test2() {
        logger.info("==>test2");
        subscribe2()
                .subscribe(
                        item -> logger.info("item: {}", item),
                        error -> logger.error(error.getMessage())
                );
        logger.info("<==test2");
    }

    private Completable handshake() {
        logger.info("==>handshake");
        return Completable.create(observer -> {
            logger.info("Making handshake");
            observer.onComplete();
        });
    }

    private Completable handshakeWithError() {
        logger.info("==>handshakeWithError");
        return Completable.create(observer -> {
            logger.info("Making handshake with error");
            observer.onError(new RuntimeException("test exception"));
        });
    }

    private Flowable<String> subscription() {
        logger.info("==>subscription");
        return Flowable.fromArray("text1", "text2", "text3");
    }

    /**
     * Bad code - <i>subscription</i> starts to work before <i>handshake</i> is finished.
     *
     * @return stream with texts
     */
    private Flowable<String> subscribe() {
        logger.info("==>subscribe");
        return handshake().andThen(subscription());
    }

    /**
     * Right code - <i>subscription</i> starts to work after <i>handshake</i> is finished.
     *
     * @return stream with texts
     */
    private Flowable<String> subscribe1() {
        logger.info("==>subscribe1");
        return handshake().andThen(Flowable.defer(this::subscription));
    }

    private Flowable<String> subscribe2() {
        logger.info("==>subscribe2");
        return handshakeWithError().andThen(Flowable.defer(this::subscription));
    }

    public static void main(String[] args) {
        logger.info("==>main");
        var c = new AndThenEx02();
        c.testBad();
        c.test1();
        c.test2();
        logger.info("<==main");
    }
}
