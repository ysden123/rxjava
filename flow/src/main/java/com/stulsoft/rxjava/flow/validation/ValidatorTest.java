/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.rxjava.flow.validation;

import io.reactivex.rxjava3.core.Completable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * @author Yuriy Stul
 */
public class ValidatorTest {
    private static final Logger logger = LoggerFactory.getLogger(ValidatorTest.class);

    public static void main(String[] args) {
        logger.info("==>main");
        test1();
        test2();
        test3();
        test4();
    }

    private static void test1() {
        logger.info("==>test1");
        Function<String, Completable> validator1 = s -> s.contains("a") ?
                Completable.error(new Exception("object contains a")) : Completable.complete();
        Function<String, Completable> validator2 = s -> s.endsWith("x") ?
                Completable.error(new Exception("object ends with x")) : Completable.complete();

        String test = "test value";
        Validator<String> v1 = new Validator<>(test)
                .withValidator(validator1)
                .withValidator(validator2);
        v1.validate().subscribe(
                        () -> logger.info("{} is valid", test),
                        error -> logger.error("{} is not valid: {}", test, error.getMessage()))
                .dispose();
    }

    private static void test2() {
        logger.info("==>test2");
        Function<String, Completable> validator1 = s -> s.contains("a") ?
                Completable.error(new Exception("object contains a")) : Completable.complete();
        Function<String, Completable> validator2 = s -> s.endsWith("x") ?
                Completable.error(new Exception("object ends with x")) : Completable.complete();

        String test = null;
        Validator<String> v1 = new Validator<>(test)
                .withValidator(validator1)
                .withValidator(validator2);
        v1.validate().subscribe(
                        () -> logger.info("{} is valid", test),
                        error -> logger.error("{} is not valid: {}", test, error.getMessage()))
                .dispose();
    }

    private static void test3() {
        logger.info("==>test3");
        validate("abcx");
        validate("uyx");
        validate("good text");
    }

    private static void test4() {
        logger.info("==>test4");
        validate2("abcx");
        validate2("uyx");
        validate2("good text");
    }

    private static void validate(String object) {
        logger.info("==>validate. object: {}", object);
        Function<String, Completable> validator1 = s -> s.contains("a") ?
                Completable.error(new Exception("object contains a")) : Completable.complete();
        Function<String, Completable> validator2 = s -> s.endsWith("x") ?
                Completable.error(new Exception("object ends with x")) : Completable.complete();

        Validator<String> v1 = new Validator<>(object)
                .withValidator(validator1)
                .withValidator(validator2);
        v1.validate().subscribe(
                        () -> logger.info("{} is valid", object),
                        error -> logger.error("{} is not valid: {}", object, error.getMessage()))
                .dispose();
    }

    private static void validate2(String object) {
        logger.info("==>validate. object: {}", object);

        Validator<String> validator = new Validator<>(object)
                .withValidator(s -> s.contains("a") ?
                        Completable.error(new Exception("object contains a")) : Completable.complete())
                .withValidator(s -> s.endsWith("x") ?
                        Completable.error(new Exception("object ends with x")) : Completable.complete());
        validator.validate().subscribe(
                        () -> logger.info("{} is valid", object),
                        error -> logger.error("{} is not valid: {}", object, error.getMessage()))
                .dispose();
    }
}
