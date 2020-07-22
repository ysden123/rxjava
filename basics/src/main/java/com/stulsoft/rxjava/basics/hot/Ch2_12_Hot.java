/*
 * Copyright (c) 2020. Yuriy Stul
 */

package com.stulsoft.rxjava.basics.hot;

import io.reactivex.rxjava3.core.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hot observables
 *
 * @author Yuriy Stul
 */
public class Ch2_12_Hot {
    private static final Logger logger = LoggerFactory.getLogger(Ch2_12_Hot.class);

    public static void main(String[] args) {
        test1();
    }

    static void test1(){
        logger.info("==>test1");

        Observable<String> source = Observable.create(observer ->{
            observer.onNext(new TestObj("Alpha").name);
            observer.onNext(new TestObj("Beta").name);
            observer.onNext(new TestObj("Gamma").name);
            observer.onComplete();
        });

        source.subscribe(s -> logger.info("Observer 1: " + s));

        source.subscribe(s -> logger.info("Observer 2: " + s));

        logger.info("<==test1");
    }

    static class TestObj {
        private final String name;

        public TestObj(String name) {
            this.name = name;
            logger.info("Initialize TestObj with {}", name);
        }
    }
}
