package hk.mcc.sample.spring.service;

import hk.mcc.sample.spring.entity.SampleUser;
import hk.mcc.sample.spring.security.SampleUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.HashSet;

@Service
public class SampleUserDetailsService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleUserDetailsService.class);
    //private HashMap<String, UserDetails> userDetailsHashSet;
    private HashMap<String, SampleUser>  sampleUserHashSet;


    @PostConstruct
    public void init(){
        LOGGER.info("SampleUserDetailsService");
        this.sampleUserHashSet = new HashMap<>();
    }

    public void addUser(SampleUser sampleUser){
        this.sampleUserHashSet.put(sampleUser.getUserName(), sampleUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SampleUser sampleUser = this.sampleUserHashSet.get(username);
        if(sampleUser!=null) {
            return new SampleUserDetails(sampleUser);
        } else {
            throw new UsernameNotFoundException("username: " + username + " not found");
        }
    }

    public void resetFailAttempts(String username) {
        SampleUser sampleUser = this.sampleUserHashSet.get(username);
        if(sampleUser!=null) {
            sampleUser.setFailedLoginAttempt(0);
        }
    }

    public void updateFailAttempts(String username) {
        SampleUser sampleUser = this.sampleUserHashSet.get(username);
        if(sampleUser!=null) {
            sampleUser.setFailedLoginAttempt(sampleUser.getFailedLoginAttempt() + 1);
        }
    }
}
