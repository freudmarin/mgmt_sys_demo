package it.sabanet.mgmt_sys_demo.exception;

import org.springframework.http.HttpStatus;

public class ASMException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public ASMException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
