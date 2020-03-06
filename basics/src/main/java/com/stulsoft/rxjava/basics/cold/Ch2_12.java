/*
 * Copyright (c) 2020. Yuriy Stul
 */

package com.stulsoft.rxjava.basics.cold;

import io.reactivex.rxjava3.core.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cold observables
 *
 * @author Yuriy Stul
 */
public class Ch2_12 {
    private static final Logger logger = LoggerFactory.getLogger(Ch2_12.class);

    public static void main(String[] args) {
        test1();
        test2();
    }

    static void test1() {
        logger.info("==>test1");
        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma");

        source.subscribe(s -> logger.info("Observer 1: " + s));

        source.map(String::length)
                .filter(i -> i >= 5)
                .subscribe(s -> logger.info("Observer 2: " + s));

        source.filter(s -> s.length() >= 5)
                .subscribe(s -> logger.info("Observer 3: " + s));

        logger.info("<==test1");
    }

    static void test2() {
        logger.info("==>test2");
        Observable<String> source = Observable.just(
                new TestObj("Alpha").name,
                new TestObj("Beta").name, new TestObj("Gamma").name);

        source.subscribe(s -> logger.info("Observer 1: " + s));

        source.map(String::length)
                .filter(i -> i >= 5)
                .subscribe(s -> logger.info("Observer 2: " + s));

        source.filter(s -> s.length() >= 5)
                .subscribe(s -> logger.info("Observer 3: " + s));

        logger.info("<==test2");
    }

    static class TestObj {
        private String name;

        public TestObj(String name) {
            this.name = name;
            logger.info("Initialize TestObj with {}", name);
        }
    }
}
