package com.example.bmw.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND(404, "Not found"),
    FORBIDDEN(403, "token expired"),
    BAD_REQUEST(400, "can not run");

    private final int httpStatus;
    private final String message;
}
