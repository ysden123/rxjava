/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.rxjava.basics.map;

import io.reactivex.rxjava3.core.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demonstrates difference between map and flatMap
 *
 * @author Yuriy Stul
 */
public class MapAndFlatMap {
    private static final Logger logger = LoggerFactory.getLogger(MapAndFlatMap.class);

    private static Single<String> service1() {
        return Single.create(source -> {
            logger.info("In service1");
            source.onSuccess("Response from service 1");
        });
    }

    private static Single<String> service2(String original) {
        return Single.create(source -> {
            logger.info("In service2");
            source.onSuccess(original.concat(" + response from service 2"));
        });
    }

    /**
     * Synchronized conversion - use map
     */
    private static void mapTest() {
        logger.info("==>mapTest");
        service1()
                .map(responseFromService1 -> responseFromService1.concat(" modified"))  // returns converted object
                .subscribe(result -> logger.info("result: {}", result));
    }

    /**
     * Asynchronous conversion (service2) - use flatMap
     */
    private static void flatMapTest() {
        logger.info("==>flatMapTest");
        service1()
                .flatMap(MapAndFlatMap::service2)   // calls a service "synchronously" and returns Observable with converted object
                .subscribe(result -> logger.info("result: {}", result));
    }

    public static void main(String[] args) {
        logger.info("==>main");
        mapTest();
        flatMapTest();
    }
}
