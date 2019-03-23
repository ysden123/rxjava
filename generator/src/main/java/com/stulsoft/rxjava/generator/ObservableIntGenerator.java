/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.rxjava.generator;

import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @author Yuriy Stul
 */
public class ObservableIntGenerator {
    private static final Logger logger = LoggerFactory.getLogger(ObservableIntGenerator.class);

    private static final Random random = new Random();

    /**
     * Emits <i>size</i> int values
     *
     * @param size number of values
     * @return stream of int values
     */
    public static Observable<Integer> generate(final int size) {
        return Observable.create(emitter -> {
            for (int i = 1; i <= size; ++i) {
                logger.debug("Emit for {} item", i);
                if (!emitter.isDisposed()) {
                    emitter.onNext(random.nextInt());
                }
            }

            if (!emitter.isDisposed()) {
                emitter.onComplete();
            }
        });
    }

    public static Observable<Integer> generateAsync(final int size) {
        return Observable.create(emitter -> new Thread(() -> {
            for (int i = 1; i <= size; ++i) {
                try {
                    Thread.sleep(500);
                } catch (Exception ignore) {
                }
                logger.debug("Emit for {} item", i);
                if (!emitter.isDisposed()) {
                    emitter.onNext(random.nextInt());
                }
            }

            if (!emitter.isDisposed()) {
                emitter.onComplete();
            }
        }).start());
    }
}
