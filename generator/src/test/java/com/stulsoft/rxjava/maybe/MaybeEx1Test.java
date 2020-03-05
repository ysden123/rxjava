/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.rxjava.maybe;

import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.jupiter.api.Test;

/**
 * @author Yuriy Stul
 */
class MaybeEx1Test {

    @Test
    void getExisting() {
        // given:
        var observer = new TestObserver<String>();
        var result = MaybeEx1.get(true);

        // when:
        result.subscribe(observer);

        // then:
        observer.assertComplete();
        observer.assertValue(text -> text != null && !text.isEmpty());
        observer.assertNoErrors();
    }

    @Test
    void getNotExisting() {
        // given:
        var observer = new TestObserver<String>();
        var result = MaybeEx1.get(false);

        // when:
        result.subscribe(observer);

        // then:
        observer.assertComplete();
        observer.assertNoValues();
        observer.assertNoErrors();
    }

    @Test
    void getWithError() {
        // given:
        var observer = new TestObserver<String>();
        var result = MaybeEx1.getWithError();

        // when:
        result.subscribe(observer);

        // then:
        observer.assertNotComplete();
        observer.assertError(RuntimeException.class);
    }
}
