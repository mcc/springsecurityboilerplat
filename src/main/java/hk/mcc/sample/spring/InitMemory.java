package hk.mcc.sample.spring;

import hk.mcc.sample.spring.entity.SampleRole;
import hk.mcc.sample.spring.entity.SampleUser;
import hk.mcc.sample.spring.service.SampleUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

@Component
public class InitMemory implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitMemory.class);

    @Autowired
    private SampleUserDetailsService sampleUserDetailsService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public InitMemory(SampleUserDetailsService sampleUserDetailsService, PasswordEncoder passwordEncoder) {
        this.sampleUserDetailsService = sampleUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOGGER.info("onApplicationEvent");
        SampleUser sampleUser = new SampleUser();
        sampleUser.setUserName("user");
        sampleUser.setEncodedPassword(this.passwordEncoder.encode("user"));
        sampleUser.setFailedLoginAttempt(0);
        Collection<SampleRole> sampleRoles = new HashSet<>();
        sampleRoles.add(new SampleRole("admin"));
        sampleUser.setRoles(sampleRoles);
        this.sampleUserDetailsService.addUser(sampleUser);

    }
}
