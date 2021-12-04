package com.chetan.userprofile.validator;

import com.chetan.userprofile.error.ErrorCode;

public enum DummyErrorCode implements ErrorCode {
    INVALID_INPUT(1, "input is not valid");

    private int errorCode;
    private String description;

    DummyErrorCode(int errorCode, String description) {
        this.errorCode = errorCode;
        this.description = description;
    }

    @Override
    public int getCode() {
        return this.errorCode;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
