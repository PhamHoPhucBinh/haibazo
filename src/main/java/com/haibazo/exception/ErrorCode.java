package com.haibazo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    CATEGORY_EXIST(1001, "Category Exist", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(1002, "Category Is Not Found", HttpStatus.NOT_FOUND),
    CATEGORY_DELETE_FAIL(1003, "Category Delete Is Failed", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(9999, "uncategorized exception error", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_NOT_FOUND(2001, "Product Not Found", HttpStatus.NOT_FOUND),
    PRODUCT_EXIST(2002, "Product Exist", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(3001, "User Not Found", HttpStatus.NOT_FOUND),
    USER_EXIST(3002, "User Exist", HttpStatus.NOT_FOUND);
    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
