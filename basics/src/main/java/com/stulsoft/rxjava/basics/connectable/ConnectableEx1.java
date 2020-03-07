/*
 * Copyright (c) 2020. Yuriy Stul
 */

package com.stulsoft.rxjava.basics.connectable;

import io.reactivex.rxjava3.core.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Connectable source
 *
 * @author Yuriy Stul
 */
public class ConnectableEx1 {
    private static final Logger logger = LoggerFactory.getLogger(ConnectableEx1.class);

    static class TestObj {
        private String name;

        public TestObj(String name) {
            this.name = name;
            logger.info("Initialize TestObj with {}", name);
        }
    }

    public static void main(String[] args) {
        logger.info("==>main");
        test1();
        logger.info("<==main");
    }

    static void test1() {
        logger.info("==>test1");
        var connectableSource = Observable.just(
                new TestObj("Alhpa").name,
                new TestObj("Beta").name,
                new TestObj("Gamma").name)
                .publish();

        connectableSource.subscribe(s -> logger.info("Subscriber 1: s = {}", s));
        connectableSource
                .map(s -> s.length())
                .subscribe(l -> logger.info("Subscriber 2: length = {}", l));
        connectableSource
                .map(s -> s + " SUFFIX")
                .subscribe(s -> logger.info("Subscriber 3: s = {}", s));

        connectableSource.connect();
        logger.info("<==test1");
    }
}
