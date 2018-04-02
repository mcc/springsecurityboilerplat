package hk.mcc.sample.spring;

import hk.mcc.sample.spring.security.SampleUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);
    @RequestMapping("/")
    private String home() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loginUser = null;
        if (principal instanceof SampleUserDetails){
            loginUser = ((SampleUserDetails) principal).getUsername();
        } else {
            loginUser = (String) principal;
        }
        LOGGER.info("login: " + loginUser );
        return "home";
        //return "Hello World!";
    }

}
