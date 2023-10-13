package com.bezkoder.spring.security.jwt.payload.request;

public class RegistrationStatus {

    private boolean isRegistered;

    public RegistrationStatus(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }
}
