package hk.mcc.sample.spring.security;

import hk.mcc.sample.spring.service.SampleUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class SampleAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    SampleUserDetailsService sampleUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            Authentication authenticate = super.authenticate(authentication);
            sampleUserDetailsService.resetFailAttempts(authentication.getName());
            return authenticate;
        } catch (BadCredentialsException ex){
            //Bad Credential
            //SampleUserDetails userDetails = (SampleUserDetails) sampleUserDetailsService.loadUserByUsername(authentication.getName());
            sampleUserDetailsService.updateFailAttempts(authentication.getName());
            throw ex;
        } catch (LockedException ex){
            //Account locked
            throw ex;
        } catch (DisabledException ex){
            //Account disabled
            throw ex;
        }
    }
}
