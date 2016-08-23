package com.code.space.androidlib.utilities;

/**
 * Created by shangxuebin on 16-5-24.
 */
public class CommonException extends RuntimeException{
    public CommonException() {
    }

    public CommonException(String detailMessage) {
        super(detailMessage);
    }

    public CommonException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public CommonException(Throwable throwable) {
        super(throwable);
    }
}
