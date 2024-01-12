package com.tntu.server.docs.core.services.mail;

import com.tntu.server.docs.core.data.exceptions.auth.CanNotSendMailException;
import com.tntu.server.docs.core.data.models.user.RoleModel;
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
