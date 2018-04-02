package hk.mcc.sample.spring.config;

import hk.mcc.sample.spring.security.SampleAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.UserDetailsAwareConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SampleAuthenticationConfigurer extends UserDetailsAwareConfigurer<AuthenticationManagerBuilder, UserDetailsService> {

    private SampleAuthenticationProvider provider = new SampleAuthenticationProvider();

    private final UserDetailsService userDetailsService;

    public SampleAuthenticationConfigurer(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.provider.setUserDetailsService(userDetailsService);
    }

    public SampleAuthenticationConfigurer passwordEncoder(PasswordEncoder passwordEncoder) {
        this.provider.setPasswordEncoder(passwordEncoder);
        return this;
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        this.provider = postProcess(this.provider);
        builder.authenticationProvider(this.provider);
    }

    @Override
    public UserDetailsService getUserDetailsService() {
        return this.userDetailsService;
    }
}
