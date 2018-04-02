package hk.mcc.sample.spring.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

public class SampleWebAuthenticationDetails extends WebAuthenticationDetails {
    /**
     * Records the remote address and will also set the session Id if a session already
     * exists (it won't create one).
     *
     * @param request that the authentication request was received from
     */
    public SampleWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
    }
}
