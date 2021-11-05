/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.rxjava.flow.validation;

import io.reactivex.rxjava3.core.Completable;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Yuriy Stul
 */
public class Validator2<T> {
    private final T object;

    record PredicateWithError<T>(Predicate<T> predicate, String error){};

    private final List<PredicateWithError<T>> validators = new LinkedList<>();

    public Validator2(T object) {
        this.object = object;
    }

    public Validator2<T> withValidator(Predicate<T> predicate, String error) {
        validators.add(new PredicateWithError<>(predicate,error));
        return this;
    }

    public Optional<String> validate() {
        if (object == null)
            return Optional.of("object is null");
        for(PredicateWithError<T> predicateWithError:validators){
            if (predicateWithError.predicate.test(object))
                return Optional.of(predicateWithError.error());
        }
        return Optional.empty();
    }
}
