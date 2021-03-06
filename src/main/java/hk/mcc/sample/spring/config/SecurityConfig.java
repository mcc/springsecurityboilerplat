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
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;

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


        CorsConfigurationSource corsConfigurationSource = new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.applyPermitDefaultValues();
//                corsConfiguration.addAllowedOrigin("http://127.0.0.1:4200");
//                corsConfiguration.addAllowedMethod("POST");
//                corsConfiguration.addAllowedMethod("GET");
//                corsConfiguration.addAllowedMethod("OPTIONS");
                corsConfiguration.setAllowCredentials(true);
                LOGGER.info("corsConfigurationSource");
                //corsConfiguration.applyPermitDefaultValues();
                return corsConfiguration;
                //return corsConfiguration;
            }
        };

        @Override
        protected void configure(HttpSecurity http) throws Exception {



            //@formatter:off
            http
                .csrf().disable().cors().configurationSource(corsConfigurationSource)
                //.csrf()
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).ignoringAntMatchers("/api/login")
            .and()
                .exceptionHandling()
                //.authenticationEntryPoint(restAuthenticationEntryPoint)
            .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
            .and()
                .formLogin().successForwardUrl("/loginSuccess")
                .authenticationDetailsSource(authenticationDetailsSource())//.authenticationDetailsSource(SampleWebAuthenticationDetails::new)
                .loginPage("/login").failureUrl("/loginFailure").loginProcessingUrl("/api/login").permitAll()
                .successForwardUrl("/loginSuccess").permitAll()
                //.successHandler(authenticationSuccessHandler)
                //.failureHandler(authenticationFailureHandler)
            .and()
                .logout();
            //http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());


            //@formatter:on
        }

        private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource() {

            return new AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails>() {

                @Override
                public WebAuthenticationDetails buildDetails(
                        HttpServletRequest request) {
                    return new SampleWebAuthenticationDetails(request);
                }

            };
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
