package com.tntu.server.docs.core.services.mail;

import com.tntu.server.docs.core.data.exceptions.auth.CanNotSendMailException;
import com.tntu.server.docs.core.options.MailOptions;
import java.util.Properties;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailSender extends JavaMailSenderImpl {

    @Autowired
    private MailOptions mailOptions;

    @PostConstruct
    public void init() {
        setHost(mailOptions.getMailHost());
        setPort(mailOptions.getMailPort());

        setUsername(mailOptions.getMailUserEmail());
        setPassword(mailOptions.getMailUserPassword());

        Properties props = getJavaMailProperties();
        props.put("mail.transport.protocol", mailOptions.getMailTransportProtocol());
        props.put("mail.smtp.auth", mailOptions.isUseSmtpAuth());
        props.put("mail.smtp.starttls.enable", mailOptions.isUseSmtpStarttls());
        props.put("mail.debug", mailOptions.isUseDebug());
    }

    public void sendMail(String email, String subject, String content) throws CanNotSendMailException {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailOptions.getMailUserEmail());
            message.setTo(email);
            message.setSubject(subject);
            message.setText(content);
            send(message);
        } catch (Exception e) {
            throw new CanNotSendMailException(email);
        }
    }

}
