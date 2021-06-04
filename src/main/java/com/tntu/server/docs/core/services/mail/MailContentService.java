package com.tntu.server.docs.core.services.mail;

import com.tntu.server.docs.core.options.MailOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailContentService {

    @Autowired
    private MailOptions mailOptions;

    public String createRegistrationContent(String roleName, String roleDescription, String registrationCode) {
        return String.format(
                mailOptions.getRegistrationMailContent(),
                roleName,
                roleDescription,
                registrationCode);
    }

}
