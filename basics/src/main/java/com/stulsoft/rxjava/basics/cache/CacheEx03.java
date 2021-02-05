/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.rxjava.basics.cache;

import io.reactivex.rxjava3.core.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Demonstrates usage the cache
 *
 * <p>
 * Usage the cache allows multiple access to observable an object many times without recreate.
 * </p>
 *
 * @author Yuriy Stul
 */
public class CacheEx03 {
    private static final Logger logger = LoggerFactory.getLogger(CacheEx03.class);

    public static void main(String[] args) {
        logger.info("==>main");
        var countDown = new CountDownLatch(2);
        Single<String> source = Single.create(
                observer -> new Thread(() -> observer.onSuccess(new TestObj("test 1").name)).start());

        var cache = source.cache();

        new Thread(() -> cache
                .subscribe(
                        anObject -> {
                            logger.info("anObject: {}", anObject);
                            countDown.countDown();
                        }
                )).start();

        new Thread(() -> cache
                .map(original -> original.concat(" length = ").concat(String.valueOf(original.length())))
                .subscribe(
                        anObject -> {
                            logger.info("anObject with length: {}", anObject);
                            countDown.countDown();
                        }
                )).start();
        try {
            countDown.await();
        } catch (Exception ignore) {
        }
    }

    static class TestObj {
        private final String name;

        public TestObj(String name) {
            this.name = name;
            logger.info("Initialize TestObj with {}", name);
        }
    }
}
