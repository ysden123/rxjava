/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.rxjava.flow;

import io.reactivex.rxjava3.core.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yuriy Stul
 */
public class Service {
    private static final Logger logger = LoggerFactory.getLogger(Service.class);

    private Service() {
    }

    public static Single<String> func1(String text) {
        logger.info("==>func1");
        if (text == null)
            return Single.error(new Exception("Service::func1 -> text is null"));
        else
            return Single.just(text + " is good");
    }

    public static Single<String> func2(String text) {
        logger.info("==>func2");
        if (text == null)
            return Single.error(new Exception("Service::func3 -> text is null"));
        else
            return Single.just(text + " is used");
    }

    public static Single<String> func3(String text1, String text2){
        logger.info("==>func3");
        if (text1==null)
            return Single.error(new Exception("text1 is null"));
        if (text2==null)
            return Single.error(new Exception("text2 is null"));

        return Single.just("Joined " + text1 + " and " + text2);
    }
}
