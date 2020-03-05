/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.rxjava.generator;

import io.reactivex.rxjava3.subscribers.TestSubscriber;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Yuriy Stul
 */
class FlowableIntGeneratorTest {

    @Test
    void generate() {
        FlowableIntGenerator.generate(5)
                .doOnComplete(() -> System.out.println("==>doOnComplete"))
                .doOnNext(i -> System.out.printf("i=%d%n", i))
                .subscribe();
    }

    @Test
    void generate2() {
        // given:
        var subscriber = new TestSubscriber<Integer>();
        var generateResult = FlowableIntGenerator.generate(5);

        // when:
        generateResult.subscribe(subscriber);

        // then:
        subscriber.assertComplete();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(5);
        subscriber.assertValueCount(5);
        subscriber.cancel();
    }

    @Test
    void generateAsync() throws Exception {
        // given:
        var subscriber = new TestSubscriber<Integer>();
        var generateResult = FlowableIntGenerator.generateAsync(5);

        // when:
        generateResult
                .doOnNext(i -> System.out.printf("i=%d%n", i))
                .doOnComplete(() -> System.out.println("==>doOnComplete"))
                .subscribe(subscriber);

        subscriber.await(5, TimeUnit.SECONDS);

        // then:
        subscriber.assertComplete();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(5);
        subscriber.cancel();
    }

    @Test
    void generateAsyncWithDisposeAtEnd() throws Exception {
        // given:
        var subscriber = new TestSubscriber<Integer>();
        var generateResult = FlowableIntGenerator.generateAsync(5);

        // when:
        generateResult
                .doOnNext(i -> System.out.printf("i=%d%n", i))
                .doOnComplete(() -> {
                    System.out.println("==>doOnComplete");
                    subscriber.cancel();
                })
                .subscribe(subscriber);

        subscriber.await(5, TimeUnit.SECONDS);

        // then:
        subscriber.assertComplete();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(5);
        subscriber.cancel();
    }

    @Test
    void generateAsyncWithDispose() throws Exception {
        // given:
        var subscriber = new TestSubscriber<Integer>();
        var generateResult = FlowableIntGenerator.generateAsync(5);

        // when:
        generateResult
                .doOnNext(i -> System.out.printf("i=%d%n", i))
                .doOnComplete(() -> {
                    System.out.println("==>doOnComplete");
                    subscriber.cancel();
                })
                .subscribe(subscriber);
        try {
            Thread.sleep(600);
            subscriber.cancel();
        } catch (Exception ignore) {
        }

        subscriber.await(5, TimeUnit.SECONDS);

        // then:
        subscriber.assertNotComplete();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.cancel();
    }
}
