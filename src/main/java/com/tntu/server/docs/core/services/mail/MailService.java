package com.tntu.server.docs.core.services.mail;

import com.tntu.server.docs.core.models.data.RoleModel;
import com.tntu.server.docs.core.models.exceptions.CanNotSendMailException;
import com.tntu.server.docs.core.options.MailOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private MailContentService mailContentService;


    public void sendRegistrationMessage(String code, String email, RoleModel roleModel) throws CanNotSendMailException {
        var content = mailContentService.createRegistrationContent(
                roleModel.getName(),
                roleModel.getDescription(),
                code);
        mailSender.sendMail(email, "Registration", content);
    }

}
