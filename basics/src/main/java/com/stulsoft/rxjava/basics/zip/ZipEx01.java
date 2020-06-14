/*
 * Copyright (c) 2020. Yuriy Stul
 */

package com.stulsoft.rxjava.basics.zip;

import io.reactivex.rxjava3.core.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Yuriy Stul
 */
public class ZipEx01 {
    private static final Logger logger = LoggerFactory.getLogger(ZipEx01.class);

    private static Single<String> generate(String item) {
        return Single.just(item);
    }

    private static void test1() {
        logger.info("==>test1");

        var runs = Arrays.asList(generate("item 1"),
                generate("item 2"),
                generate("item 3"));

        Single.zip(runs, tmp -> {
            logger.debug("tmp: {}", tmp);
            logger.debug("tmp.length: {}", tmp.length);
            var result = new StringBuilder();
            runs.forEach(run -> {
                var theRun = run.blockingGet();
                logger.debug("run: {}", theRun);
                result.append(theRun);
                result.append(", ");
            });
            return result.toString();
        })
                .subscribe(rslt -> {
                            logger.info("rslt: {}", rslt);
                        },
                        err -> {
                            logger.error(err.getMessage());
                        });

    }

    private static void test2() {
        logger.info("==>test2");
        Single.zip(
                Single.just(new ArrayList<>()),
                Single.just(Arrays.asList("item 1", "item 2", "item 3", "item 4")),
                (d1, d2) -> {
                    logger.info("d1: {}, d2: {}", d1, d2);
                    return String.format("%s - %s", d1, d2);
                }
        )
                .subscribe(rslt -> {
                            logger.info("rslt: {}", rslt);
                        },
                        err -> {
                            logger.error(err.getMessage());
                        });
    }

    private static void test3() {
        logger.info("==>test3");
        Single.zip(
                Single.just(Arrays.asList("item 1", "item 2", "item 3", "item 4")),
                Single.just(Arrays.asList("item 10", "item 20", "item 30", "item 40")),
                (d1, d2) -> {
                    logger.info("d1: {}, d2: {}", d1, d2);
                    return String.format("%s - %s", d1, d2);
                }
        )
                .subscribe(rslt -> {
                            logger.info("rslt: {}", rslt);
                        },
                        err -> {
                            logger.error(err.getMessage());
                        });
    }

    private static void test4() {
        logger.info("==>test4");

        var runs = Arrays.asList(generate("item 1"),
                generate("item 2"),
                generate("item 3"));

        Single.zip(runs, replayArray -> {
            var result = new StringBuilder();
            logger.debug("replayArray.length: {}", replayArray.length);
            for (var i = 0; i < replayArray.length; ++i) {
                var replay = replayArray[i].toString();
                logger.debug("replay: {}", replay);
                result.append(replay + " modified, ");
            }
            return result.toString();
        })
                .subscribe(rslt -> {
                            logger.info("rslt: {}", rslt);
                        },
                        err -> {
                            logger.error(err.getMessage());
                        });

    }

    public static void main(String[] args) {
        logger.info("==>main");
        test1();
        test2();
        test3();
        test4();
        try {
            Thread.sleep(500);
        } catch (Exception ignore) {
        }
    }
}
