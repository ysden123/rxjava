/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.rxjava.generator;

import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Yuriy Stul
 */
class ObservableIntGeneratorTest {

    @Test
    void generate() {
        ObservableIntGenerator.generate(5)
                .doOnComplete(() -> System.out.println("==>doOnComplete"))
                .doOnNext(i -> System.out.printf("i=%d%n", i))
                .subscribe();
    }

    @Test
    void generateTest2() {
        // given:
        var observer = new TestObserver<Integer>();
        var generateResult = ObservableIntGenerator.generate(5);

        // when:
        generateResult.subscribe(observer);

        // then:
        observer.assertComplete();
        observer.assertNoErrors();
        observer.assertValueCount(5);
        observer.dispose();
    }

    @Test
    void generateAsync() throws Exception {
        // given:
        var observer = new TestObserver<Integer>();
        var generateResult = ObservableIntGenerator.generateAsync(5);

        // when:
        generateResult
                .doOnNext(i -> System.out.printf("i=%d%n", i))
                .doOnComplete(() -> System.out.println("==>doOnComplete"))
                .subscribe(observer);

        observer.await(5, TimeUnit.SECONDS);

        // then:
        observer.assertComplete();
        observer.assertNoErrors();
        observer.assertValueCount(5);
        observer.dispose();
    }

    @Test
    void generateAsyncWithDisposeAtEnd() throws Exception {
        // given:
        var observer = new TestObserver<Integer>();
        var generateResult = ObservableIntGenerator.generateAsync(5);

        // when:
        generateResult
                .doOnNext(i -> System.out.printf("i=%d%n", i))
                .doOnComplete(() -> {
                    System.out.println("==>doOnComplete");
                    observer.dispose();
                })
                .subscribe(observer);

        observer.await(5, TimeUnit.SECONDS);

        // then:
        observer.assertComplete();
        observer.assertNoErrors();
        observer.assertValueCount(5);
    }

    @Test
    void generateAsyncWithDispose() throws Exception {
        // given:
        var observer = new TestObserver<Integer>();
        var generateResult = ObservableIntGenerator.generateAsync(5);

        // when:
        generateResult
                .doOnNext(i -> System.out.printf("i=%d%n", i))
                .doOnComplete(() -> System.out.println("==>doOnComplete"))
                .subscribe(observer);

        try {
            Thread.sleep(600);
            observer.dispose();
        } catch (Exception ignore) {
        }

        observer.await(5, TimeUnit.SECONDS);

        // then:
        observer.assertNotComplete();
        observer.assertNoErrors();
        observer.assertValueCount(1);
        observer.dispose();
    }
}
