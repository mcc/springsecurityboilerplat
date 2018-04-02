package hk.mcc.sample.spring.config;

import hk.mcc.sample.spring.security.RestAuthenticationEntryPoint;
import hk.mcc.sample.spring.security.RestAuthenticationFailureHandler;
import hk.mcc.sample.spring.security.RestAuthenticationSuccessHandler;
import hk.mcc.sample.spring.security.SampleWebAuthenticationDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Configuration
    public static class DefaultWebSecurityConfigurerAdapter
            extends WebSecurityConfigurerAdapter {

        @Autowired
        private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

        @Autowired
        private RestAuthenticationSuccessHandler authenticationSuccessHandler;

        @Autowired
        private RestAuthenticationFailureHandler authenticationFailureHandler;

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Autowired
        public void configureGlobal(UserDetailsService userDetailsService,
                                    AuthenticationManagerBuilder auth) throws Exception {
            LOGGER.info("security config");
            SampleAuthenticationConfigurer configurer = new SampleAuthenticationConfigurer(
                    userDetailsService).passwordEncoder(passwordEncoder());
            auth.apply(configurer);
        }

        @Override
        public void configure(WebSecurity builder) throws Exception {
            builder.ignoring().antMatchers("/robots.txt", "/**/*.png");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //@formatter:off
            http
                //.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
            .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
            .and()
                .formLogin()
                .authenticationDetailsSource(SampleWebAuthenticationDetails::new)
                .loginPage("/login").failureUrl("/login?error").permitAll()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
            .and()
                .logout();
            //@formatter:on
        }

        @Bean
        public RestAuthenticationSuccessHandler mySuccessHandler(){
            return new RestAuthenticationSuccessHandler();
        }
        @Bean
        public SimpleUrlAuthenticationFailureHandler myFailureHandler(){
            return new RestAuthenticationFailureHandler();
        }

    }

}