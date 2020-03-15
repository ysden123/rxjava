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
public class Step1 {
    private static final Logger logger = LoggerFactory.getLogger(Step1.class);
    public Single<String> run(){
        try{
            logger.info("==>run");
            Thread.sleep(1000);
            logger.info("<==run");
            return Single.just("From step1");
        }catch(Exception ignore){
            return Single.error(new RuntimeException("Test exception from Step1"));
        }
    }
}
