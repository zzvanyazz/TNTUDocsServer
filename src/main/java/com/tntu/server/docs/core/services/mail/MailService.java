package com.tntu.server.docs.core.services.mail;

import com.tntu.server.docs.core.models.data.RoleModel;
import com.tntu.server.docs.core.models.exceptions.CanNotSendMailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailSender mailSender;
    private final MailContentService mailContentService;


    public void sendRegistrationMessage(String code, String email, RoleModel roleModel) throws CanNotSendMailException {
        var content = mailContentService.createRegistrationContent(
                roleModel.getName(),
                roleModel.getDescription(),
                code);
        mailSender.sendMail(email, "Registration", content);
    }

}
