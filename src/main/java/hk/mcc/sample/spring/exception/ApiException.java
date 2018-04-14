package hk.mcc.sample.spring.exception;

import java.util.Date;

public class ApiException extends Exception {
    private ErrorCode errorCode;
    private Date timestamp;
    public ApiException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.timestamp = new Date();
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
