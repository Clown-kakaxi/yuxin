package com.ytec.mdm.interfaces.socket.http.tools;

public class RequestTooLargeException  extends Exception {

    private static final long serialVersionUID = 1L;

    public RequestTooLargeException(String msg) {
        super(msg);
    }
}
