package com.tntu.server.docs.communication.models.requests;

public class StartRegistrationRequest {

    private String registrationCode;
    private String userName;
    private String normalisedUserName;
    private String password;

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNormalisedUserName() {
        return normalisedUserName;
    }

    public void setNormalisedUserName(String normalisedUserName) {
        this.normalisedUserName = normalisedUserName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
