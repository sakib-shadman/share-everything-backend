package com.shareeverything.exception;

/**
 * Created by @sad on 03-Jan-20.
 */
public class ShareEverythingException extends RuntimeException {
    public ShareEverythingException(String message, Exception ex) {
        super(message, ex);
    }

    public ShareEverythingException(Exception ex) {
        super(ex);
    }

    public ShareEverythingException(String message) {
        super(message);
    }
}
