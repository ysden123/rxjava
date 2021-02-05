/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.rxjava.basics.cache;

import io.reactivex.rxjava3.core.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demonstrates usage the cache
 *
 * <p>
 * Usage the cache allows multiple access to observable an object many times without recreate.
 * </p>
 *
 * @author Yuriy Stul
 */
public class CacheEx02 {
    private static final Logger logger = LoggerFactory.getLogger(CacheEx02.class);

    public static void main(String[] args) {
        logger.info("==>main");
        Single<String> source = Single.create(observer -> observer.onSuccess(new TestObj("test 1").name));

        var cache = source.cache();

        cache
                .subscribe(
                        anObject -> logger.info("anObject: {}", anObject)
                );

        cache
                .map(original -> original.concat(" length = ").concat(String.valueOf(original.length())))
                .subscribe(
                        anObject -> logger.info("anObject with length: {}", anObject)
                );
    }

    static class TestObj {
        private final String name;

        public TestObj(String name) {
            this.name = name;
            logger.info("Initialize TestObj with {}", name);
        }
    }
}
