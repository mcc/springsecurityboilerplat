package hk.mcc.sample.spring;

import hk.mcc.sample.spring.service.SampleUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private SampleUserDetailsService sampleUserDetailsService;

    @RequestMapping(value = "/login")
    public String home(Map<String, Object> model) {
        for (Map.Entry<String, Object> entry : model.entrySet()) {
            LOGGER.info(entry.getKey() + " : " + entry.getValue());
        }
        return "login";
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
