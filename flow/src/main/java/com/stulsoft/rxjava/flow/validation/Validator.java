/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.rxjava.flow.validation;

import io.reactivex.rxjava3.core.Completable;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Yuriy Stul
 */
public class Validator<T> {
    private final T object;

    private final List<Function<T, Completable>> validators = new LinkedList<>();

    public Validator(T object) {
        this.object = object;
    }

    public Validator<T> withValidator(Function<T, Completable> validator) {
        validators.add(validator);
        return this;
    }

    public Completable validate() {
        return Completable.create(source -> {
            if (object == null)
                source.onError(new Exception("object is null"));
            else {
                Completable[] validationResults = new Completable[validators.size()];
                for (int i = 0; i < validators.size(); ++i)
                    validationResults[i] = validators.get(i).apply(object);
                Completable.concatArray(validationResults)
                        .subscribe(
                                source::onComplete,
                                source::onError
                        )
                        .dispose();
            }
        });
    }
}
