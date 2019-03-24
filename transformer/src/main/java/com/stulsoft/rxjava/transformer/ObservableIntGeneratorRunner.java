/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.rxjava.transformer;

import com.stulsoft.rxjava.generator.ObservableIntGenerator;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Example of chained usage
 *
 * @author Yuriy Stul
 */
public class ObservableIntGeneratorRunner {
    private static final Logger logger = LoggerFactory.getLogger(ObservableIntGeneratorRunner.class);

    private void run() {
        logger.info("==>run");
        size()
                .doOnSuccess(size -> logger.info("Size = {}", size))
                .flatMapObservable(ObservableIntGenerator::generate)
                .subscribe(
                        i -> logger.info("i = {}", i),
                        error -> logger.error(error.getMessage()));
        logger.info("<==run");
    }

    private void runWithError() {
        logger.info("==>runWithError");
        sizeError()
                .doOnSuccess(size -> logger.info("Size = {}", size))
                .doOnError(error -> logger.error("(1) {}", error.getMessage()))
                .flatMapObservable(ObservableIntGenerator::generate)
                .doOnError(error -> logger.error("(2) {}", error.getMessage()))
                .subscribe(
                        i -> logger.info("i = {}", i),
                        error -> logger.error("(3) {}", error.getMessage()));
        logger.info("<==runWithError");
    }

    private Single<Integer> size() {
        return Single.just(new Random().nextInt(5));
    }

    private Single<Integer> sizeError() {
        return Single.error(new RuntimeException("Test exception"));
    }

    public static void main(String[] args) {
        logger.info("==>main");
        new ObservableIntGeneratorRunner().run();
        new ObservableIntGeneratorRunner().runWithError();
        logger.info("<==main");
    }
}
