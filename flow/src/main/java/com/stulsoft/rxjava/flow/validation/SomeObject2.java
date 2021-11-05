/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.rxjava.flow.validation;

import java.util.Optional;

/**
 * @author Yuriy Stul
 */
public record SomeObject2(String name, Integer age) {
    public Optional<String> validate() {
        var validator = new Validator2<>(this)
                .withValidator(so -> (so.name() == null), "name is null")
                .withValidator(so -> (so.age() == null), "age is null")
                .withValidator(so -> (so.age() > 20), "age is grater than 20");
        return validator.validate();
    }
}
