package com.gl.si.notifications;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TaskExecutor taskExecutor;

    public EmailService(JavaMailSender javaMailSender, TaskExecutor taskExecutor) {
        this.javaMailSender = javaMailSender;
        this.taskExecutor = taskExecutor;
    }

    public void sendRapportPdf(String email,String subject,String body,byte[] pdf) throws MessagingException {
        log.info("Sending email rapport pdf to {}",email);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);

        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.addAttachment("rapport.pdf",new ByteArrayResource(pdf));
        javaMailSender.send(mimeMessage);
        log.info("Email rapport pdf sent to {}",email);
    }

    public void sendEmailRapportPdfAsync(String email,String subject,String body,byte[] pdf) {
        taskExecutor.execute(() -> {
            try {
                sendRapportPdf(email, subject, body, pdf);
            } catch (MessagingException e) {
                log.error("Failed to send email rapport pdf to {}",email,e);
            }
        });
    }
}
