package com.tntu.server.docs.core.options;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailOptions {

   /* @Value("${mail.content.registration}")
    private String registrationMailContent;*/
    @Value("${mail.host}")
    private String mailHost;
    @Value("${mail.port}")
    private int mailPort;
    @Value("${mail.user.email}")
    private String mailUserEmail;
    @Value("${mail.user.password}")
    private String mailUserPassword;
    @Value("${mail.transport.protocol}")
    private String mailTransportProtocol;
    @Value("${mail.smtp.auth}")
    private boolean useSmtpAuth;
    @Value("${mail.smtp.starttls.enable}")
    private boolean useSmtpStarttls;
    @Value("${mail.debug}")
    private boolean useDebug;


   /*public String getRegistrationMailContent() {
        return registrationMailContent;
    }*/

    public String getMailHost() {
        return mailHost;
    }

    public int getMailPort() {
        return mailPort;
    }

    public String getMailUserEmail() {
        return mailUserEmail;
    }

    public String getMailUserPassword() {
        return mailUserPassword;
    }

    public String getMailTransportProtocol() {
        return mailTransportProtocol;
    }

    public boolean useSmtpAuth() {
        return useSmtpAuth;
    }

    public boolean useSmtpStarttls() {
        return useSmtpStarttls;
    }

    public boolean useDebug() {
        return useDebug;
    }
}
