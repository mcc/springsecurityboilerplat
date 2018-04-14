package hk.mcc.sample.spring.exception;

public enum ErrorCode {
    INVALID_DATA("10001", "Invalid Data"),
    INVALID_REQUEST("10001", "Invalid Data");

    final String code;
    final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
