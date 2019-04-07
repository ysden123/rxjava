/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.rxjava.generator;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @author Yuriy Stul
 */
public class FlowableIntGenerator {
    private static final Logger logger = LoggerFactory.getLogger(FlowableIntGenerator.class);

    private static final Random random = new Random();

    public static Flowable<Integer> generate(final int size) {
        return Flowable.create(emitter -> {
            for (int i = 1; i <= size; ++i) {
                logger.debug("Emit for {} item", i);
                if (!emitter.isCancelled())
                    emitter.onNext(random.nextInt());
            }
            if (!emitter.isCancelled())
                emitter.onComplete();
        }, BackpressureStrategy.BUFFER);
    }

    public static Flowable<Integer> generateAsync(final int size) {
        return Flowable.create(emitter -> new Thread(() -> {
                    for (int i = 1; i <= size; ++i) {
                        try {
                            Thread.sleep(500);
                        } catch (Exception ignore) {
                        }
                        logger.debug("Emit for {} item", i);
                        if (!emitter.isCancelled())
                            emitter.onNext(random.nextInt());
                    }
                    if (!emitter.isCancelled())
                        emitter.onComplete();
                }).start(),
                BackpressureStrategy.BUFFER);
    }
}
