package hk.mcc.sample.spring.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ApiErrorResponse {
    private HttpStatus httpStatus;
    private String message;
    private String code;
    private String type;
    private Date timestamp;
    private String path;

    public ApiErrorResponse(HttpStatus httpStatus, String code, String message, String type, Date occurTime, String path) {
        this.timestamp = occurTime;
        this.httpStatus = httpStatus;
        this.message = message;
        this.code = code;
        this.type = type;
        this.path = path;
    }

    public int getStatus() {
        return this.httpStatus.value();
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getError() {
        return this.httpStatus.getReasonPhrase();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }
}
