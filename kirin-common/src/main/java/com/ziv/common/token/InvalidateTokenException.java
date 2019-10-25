package com.ziv.common.token;

/**
 * token无效异常
 *
 * @author ziv
 * @date 2019-10-25
 */
public class InvalidateTokenException extends Exception {

    public InvalidateTokenException() {

    }

    public InvalidateTokenException(String message) {
        super(message);
    }
}
