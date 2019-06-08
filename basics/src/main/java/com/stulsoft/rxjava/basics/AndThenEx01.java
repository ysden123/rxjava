/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.rxjava.basics;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demonstrates usage the <i>andThen</i>
 *
 * @author Yuriy Stul
 */
public class AndThenEx01 {
    private static final Logger logger = LoggerFactory.getLogger(AndThenEx01.class);

    private void test1() {
        logger.info("==>test1");
        subscribe()
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
    private Flowable<String> subscribe2() {
        logger.info("==>subscribe2");
        return Flowable.create(emitter ->
                        handshake()
                                .subscribe(() ->
                                                subscription()
                                                        .subscribe(emitter::onNext,
                                                                emitter::onError),
                                        emitter::onError),
                BackpressureStrategy.BUFFER);

    }

    public static void main(String[] args) {
        logger.info("==>main");
        var c = new AndThenEx01();
        c.test1();
        c.test2();
        logger.info("<==main");
    }
}
