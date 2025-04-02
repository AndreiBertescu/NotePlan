package com.noteplan.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    /**
     * JavaMailSender interface.
     */
    private JavaMailSender javaMailSender;

    /**
     * gets mail username from properties.
     */
    @Value("${spring.mail.username}")
    private String host_email;

    /**
     * constructs an instance of EmailService with the specified JavaMailSender.
     *
     * @param javaMailSender.
     *
     * @return EmailService.
     */
    public EmailService(final JavaMailSender newJavaMailSender) {
        this.javaMailSender = newJavaMailSender;
    }

    /**
     * sends a confirmation email.
     *
     * @param email.
     * @param confirmationToken.
     * @param url.
     */
    @Async
    public void sendEmail(final String email, final String confirmationToken, String url) throws MessagingException {
        url += "confirm-account?token=" + confirmationToken;
        String mailContent = "<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "  <title>Account Verification</title>\n"
                + "</head>\n" + "<body>\n"
                + "  <div style=\"font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;\">\n"
                + "    <h2>Welcome to NotePlan!</h2>\n"
                + "    <p>Thank you for signing up. To verify your account, please click the button below:</p>\n"
                + "    <a href=\"" + url
                + "\" style=\"display: inline-block; padding: 12px 24px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 4px;\">\n"
                + "      Verify Account\n" + "    </a>\n"
                + "    <p><strong>If you didn't register on NotePlan, please ignore this email.</strong></p>\n"
                + "    <p>If the button above doesn't work, you can also <strong><a href=\"" + url
                + "\">click here</a></strong> or copy and paste the following link into your browser:</p>\n"
                + "    <p><strong>" + url + "</strong></p>\n" + "    <p>Thank you,<br/>The NotePlan Team</p>\n"
                + "  </div>\n" + "</body>\n" + "</html>";

        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);

        helper.setTo(email);
        helper.setSubject("Complete Registration!");
        helper.setFrom(host_email);

        helper.setText(mailContent, true);

        javaMailSender.send(mailMessage);

    }
}
