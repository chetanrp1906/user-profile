package com.chetan.userprofile.user.error;

import com.chetan.userprofile.error.ErrorCode;

public enum UserErrorCode implements ErrorCode {

    FIRST_NAME_REQUIRED(1, "First name is mandatory"),
    EMAIL_REQUIRED(2, "Email is mandatory"),
    PASSWORD_REQUIRED(3, "Password is mandatory"),
    INVALID_PASSWORD(4, "Password is not valid"),
    EMAIL_IN_USE(5, "Email is already in use");

    private int errorCode;
    private String description;

    UserErrorCode(int errorCode, String description) {
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
