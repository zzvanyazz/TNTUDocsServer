package com.tntu.server.docs.core.options;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
public class MailOptions {

    @Value("${mail.content.registration}")
    private String registrationMailContent;
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

}
