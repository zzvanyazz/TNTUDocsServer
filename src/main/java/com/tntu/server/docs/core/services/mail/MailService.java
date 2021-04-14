package com.tntu.server.docs.core.services.mail;

import com.tntu.server.docs.core.data.models.user.RoleModel;
import com.tntu.server.docs.core.data.exceptions.auth.CanNotSendMailException;
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
