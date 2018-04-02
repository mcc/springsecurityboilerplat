package hk.mcc.sample.spring.entity;

import java.util.Collection;

public class SampleUser {
    private Long SampleUserId;
    private String userName;
    private String encodedPassword;
    private int failedLoginAttempt;
    private Collection<SampleRole> roles;

    public Long getSampleUserId() {
        return SampleUserId;
    }

    public void setSampleUserId(Long sampleUserId) {
        SampleUserId = sampleUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public int getFailedLoginAttempt() {
        return failedLoginAttempt;
    }

    public void setFailedLoginAttempt(int failedLoginAttempt) {
        this.failedLoginAttempt = failedLoginAttempt;
    }

    public Collection<SampleRole> getRoles() {
        return roles;
    }

    public void setRoles(Collection<SampleRole> roles) {
        this.roles = roles;
    }
}
