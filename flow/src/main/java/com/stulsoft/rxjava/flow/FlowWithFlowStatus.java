/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.rxjava.flow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yuriy Stul
 */
public class FlowWithFlowStatus {
    private static final Logger logger = LoggerFactory.getLogger(FlowWithFlowStatus.class);

    record FlowStatus(String text1, String text2, String text3) {
    }

    public static void main(String[] args) {
        logger.info("==>main");

        flow("Good text");
        flow(null);
    }

    private static void flow(String text) {
        logger.info("==>flow. text: {}", text);

        Service.func1(text).map(text1 -> new FlowStatus(text1, null, null))
                .flatMap(flowStatus -> Service.func2(flowStatus.text1())
                        .map(text2 -> new FlowStatus(flowStatus.text1(), text2, null)))
                .flatMap(flowStatus -> Service.func3(flowStatus.text1(), flowStatus.text2())
                        .map(text3 -> new FlowStatus(flowStatus.text1(), flowStatus.text2(), text3)))
                .subscribe(
                        flowStatus -> logger.info(flowStatus.toString()),
                        error -> logger.error("Failure: {}", error.getMessage())
                )
                .dispose();
    }
}
