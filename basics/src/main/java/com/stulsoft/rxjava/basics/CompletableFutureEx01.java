/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.rxjava.basics;

import io.reactivex.Completable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @author Yuriy Stul
 */
public class CompletableFutureEx01 {
    private static final Logger logger = LoggerFactory.getLogger(CompletableFutureEx01.class);

    private Completable someCompletable(){
        logger.info("==>someCompletable");
        return Completable.create(observer ->{
           observer.onComplete();
            logger.info("<==someCompletable");
        });
    }

    private void test1(){
        logger.info("==>test1");
        someCompletable()
                .subscribe(()->logger.info("Completed"),error-> logger.error(error.getMessage()));
        logger.info("<==test1");
    }

    public static void main(String[] args) {
        logger.info("==>main");
        var c = new CompletableFutureEx01();
        c.test1();
        logger.info("<==main");
    }
}
