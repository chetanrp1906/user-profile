package com.chetan.userprofile.error;

import lombok.*;

import java.io.Serializable;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Error implements Serializable {
    private int code;
    private String description;

    public static Optional<Error> createOptionalError(ErrorCode errorCode) {
        return Optional.of(new Error(errorCode.getCode(), errorCode.getDescription()));
    }
}
