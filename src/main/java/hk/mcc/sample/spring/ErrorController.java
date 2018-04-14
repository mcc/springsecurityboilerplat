package hk.mcc.sample.spring;

import hk.mcc.sample.spring.exception.ApiException;
import hk.mcc.sample.spring.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/error_sample")
public class ErrorController {
    @RequestMapping("/exception")
    public ResponseEntity<?> throwException() throws ApiException {
        throw new ApiException(ErrorCode.INVALID_DATA, "some custom message");
    }

}
