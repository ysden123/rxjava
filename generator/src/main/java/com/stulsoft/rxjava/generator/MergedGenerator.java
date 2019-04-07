/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.rxjava.generator;

import io.reactivex.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yuriy Stul
 */
public class MergedGenerator {
    private static final Logger logger = LoggerFactory.getLogger(MergedGenerator.class);

    public static void main(String[] args) {
        logger.info("==>main");
        var mg = new MergedGenerator();
        mg.test1();
        mg.test2();
        mg.test3();
        logger.info("<==main");
    }

    private Flowable<Integer> generate1(int size, Flowable<Integer> previous) {
        logger.info("==>generate1: size = {}", size);
        if (size > 0)
            return FlowableIntGenerator
                    .generate(size)
                    .mergeWith(this.generate1(size - 1, previous));
        else
            return previous;
    }

    private Flowable<Integer> generate2(int size, Flowable<Integer> previous) {
        logger.info("==>generate2: size = {}", size);
        if (size > 0)
            return FlowableIntGenerator
                    .generate(size)
                    .concatWith(this.generate1(size - 1, previous));
        else
            return previous;
    }

    private void test1() {
        logger.info("==>test1");
        FlowableIntGenerator.generate(3)
                .mergeWith(FlowableIntGenerator.generate(4))
                .doOnComplete(() -> logger.info("Completed!"))
                .subscribe(
                        i -> logger.info("i={}", i),
                        error -> logger.error(error.getMessage())
                );
        logger.info("<==test1");
    }

    private void test2() {
        logger.info("==>test2");
        generate1(3, Flowable.empty())
                .subscribe(
                        i -> logger.info("i = {}", i),
                        error -> logger.error(error.getMessage(), error));
        logger.info("<==test2");
    }

    private void test3() {
        logger.info("==>test3");
        generate2(3, Flowable.empty())
                .subscribe(
                        i -> logger.info("i = {}", i),
                        error -> logger.error(error.getMessage(), error));
        logger.info("<==test3");
    }
}
