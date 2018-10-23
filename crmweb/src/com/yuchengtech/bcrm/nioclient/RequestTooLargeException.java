package com.yuchengtech.bcrm.nioclient;

public class RequestTooLargeException  extends Exception {

    private static final long serialVersionUID = 1L;

    public RequestTooLargeException(String msg) {
        super(msg);
    }
}
