package hk.mcc.sample.spring;

import hk.mcc.sample.spring.service.SampleUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private SampleUserDetailsService sampleUserDetailsService;

    @Autowired
    private HttpServletRequest context;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String home(Map<String, Object> model) {
        for (Map.Entry<String, Object> entry : model.entrySet()) {
            LOGGER.info(entry.getKey() + " : " + entry.getValue());
        }
        Exception lastException = (Exception) context.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        if(lastException!=null){
            LOGGER.info(lastException.getMessage());
        }
        return "login";
    }

    @RequestMapping(value = "/loginFailure")
    public ResponseEntity<?> loginFailure() {
        Exception lastException = (Exception) context.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        if(lastException!=null){
            LOGGER.info(lastException.getMessage());
            return new ResponseEntity(lastException.getMessage(), HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), HttpStatus.UNAUTHORIZED);
        }
    }


    @RequestMapping(value = "/home")
    public ResponseEntity<?> home() {
        return new ResponseEntity(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), HttpStatus.OK);
    }


/*
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Map<String, Object> model) {
        for (Map.Entry<String, Object> entry : model.entrySet()) {
            LOGGER.info(entry.getKey() + " : " + entry.getValue());
        }
        return "login";
    }*/



}
