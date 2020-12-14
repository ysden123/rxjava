/*
 * Copyright (c) 2020. Yuriy Stul
 */

package com.stulsoft.rxjava.basics;

/**
 * @author Yuriy Stul
 */
public class RunResult {
    private final boolean valid;
    private final String answer;

    public RunResult(boolean valid, String answer) {
        this.valid = valid;
        this.answer = answer;
    }

    public boolean isValid() {
        return valid;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "RunResult{" +
                "valid=" + valid +
                ", answer='" + answer + '\'' +
                '}';
    }
}
