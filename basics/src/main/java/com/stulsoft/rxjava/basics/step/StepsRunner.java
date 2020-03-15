/*
 * Copyright (c) 2020. Yuriy Stul
 */

package com.stulsoft.rxjava.basics.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yuriy Stul
 */
public class StepsRunner {
    private static final Logger logger = LoggerFactory.getLogger(StepsRunner.class);

    public static void main(String[] args) {
        logger.info("==>main");
        test1();
        test1Err();
        logger.info("<==main");
    }

    static void test1() {
        logger.info("==>test1");
        var step0 = new Step0();
        step0.run().subscribe(
                response -> logger.info("Response {}", response),
                err -> logger.error(err.getMessage())
        );
        logger.info("<==test1");
    }

    static void test1Err() {
        logger.info("==>test1Err");
        var step0Err = new Step0Err();
        step0Err.run().subscribe(
                response -> logger.info("Response {}", response),
                err -> logger.error(err.getMessage())
        );
        logger.info("<==test1Err");
    }
}
