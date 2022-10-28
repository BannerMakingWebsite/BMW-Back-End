package com.example.bmw.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND(404, "Not found");

    private final int httpStatus;
    private final String message;
}
