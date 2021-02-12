package com.tntu.server.docs.core.services.mail;

import com.tntu.server.docs.core.models.exceptions.CanNotSendMailException;
import com.tntu.server.docs.core.options.MailOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Service
public class MailSender extends JavaMailSenderImpl {

    @Autowired
    private MailOptions mailOptions;

    @PostConstruct
    private void init() {
        setHost(mailOptions.getMailHost());
        setPort(mailOptions.getMailPort());

        setUsername(mailOptions.getMailUserEmail());
        setPassword(mailOptions.getMailUserPassword());

        Properties props = getJavaMailProperties();
        props.put("mail.transport.protocol", mailOptions.getMailTransportProtocol());
        props.put("mail.smtp.auth", mailOptions.useSmtpAuth());
        props.put("mail.smtp.starttls.enable", mailOptions.useSmtpStarttls());
        props.put("mail.debug", mailOptions.useDebug());
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
