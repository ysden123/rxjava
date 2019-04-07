/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.rxjava.maybe;

import io.reactivex.Maybe;

/**
 * @author Yuriy Stul
 */
public class MaybeEx1 {
    private MaybeEx1() {
    }

    public static Maybe<String> get(final boolean isExists) {
        if (isExists)
            return Maybe.just("Some object");
        else
            return Maybe.empty();
    }

    public static Maybe<String> getWithError(){
        return Maybe.error(new RuntimeException("Test exception"));
    }
}
