/*
 * Copyright (c) 2020. Yuriy Stul
 */

package com.stulsoft.rxjava.basics.step;

import io.reactivex.rxjava3.core.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yuriy Stul
 */
public class Step0Err {
    private static final Logger logger = LoggerFactory.getLogger(Step0Err.class);

    private Step1Err step1 = new Step1Err();

    public Single<String> run() {
        return step1.run()
                .flatMap(response -> Single.just(response))
                .onErrorReturn(err -> {
                    logger.error(err.getMessage());
                    return "With error from Step1 " + err.getMessage();
                });
    }
}
