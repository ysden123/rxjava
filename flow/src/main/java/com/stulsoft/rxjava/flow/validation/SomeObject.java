/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.rxjava.flow.validation;

import static io.reactivex.rxjava3.core.Completable.error;
import static io.reactivex.rxjava3.core.Completable.complete;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Yuriy Stul
 */
public record SomeObject(String name, Integer age) {
    public Optional<String> validate() {
        var validator = new Validator<>(this)
                .withValidator(so -> so.name() == null ?
                        error(new Exception("name is null")) :
                        complete())
                .withValidator(so -> so.age() == null ?
                        error(new Exception("age is null")) :
                        complete())
                .withValidator(so -> (so.age() != null) && (so.age() > 20) ?
                        error(new Exception("age is more than 30")) :
                        complete());
        AtomicReference<Optional<String>> result = new AtomicReference<>(Optional.empty());
        validator
                .validate()
                .subscribe(
                        () -> result.set(Optional.empty()),
                        error -> result.set(Optional.of(error.getMessage())))
                .dispose();
        return result.get();
    }
}
