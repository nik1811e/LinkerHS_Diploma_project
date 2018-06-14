package util;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.String.valueOf;


public class MailUtil {
    private static final Logger LOGGER = Logger.getLogger(MailUtil.class);
    private static String timeNow
            = new SimpleDateFormat(FinalValueUtil.PATTERN_FULL_DATE_TIME).format(new Date().getTime());
    private Map<String, File> attachments = new HashMap<>();

    private void setupParametersForMessage(String email, String subject, String mailBody, String contentType) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", FinalValueUtil.EMAIL_HOST);
            props.put("mail.smtp.port", FinalValueUtil.EMAIL_PORT);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(FinalValueUtil.EMAIL_SUPPORT, FinalValueUtil.EMAIL_SUPPORT_PASSWORD);
                }
            };
            Session session = Session.getInstance(props, auth);
            System.setProperty("https.protocols", "TLSv1.1");

            Message message = new MimeMessage(session);

            if (!attachments.isEmpty()) {
                Multipart multipart = new MimeMultipart();

                for (Map.Entry<String, File> entry : attachments.entrySet()) {
                    MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                    attachmentBodyPart.attachFile(new File(valueOf(entry.getValue())));
                    multipart.addBodyPart(attachmentBodyPart);
                }

                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(mailBody, contentType);
                multipart.addBodyPart(messageBodyPart);

                message.setContent(multipart);
            } else {
                message.setContent(mailBody, contentType);
            }

            message.setFrom(new InternetAddress(FinalValueUtil.EMAIL_SUPPORT));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(FinalValueUtil.EMAIL_TITLE_PART + subject);
            Transport.send(message);
            LOGGER.info("Sent message to [ " + email + " ] successfully.");
        } catch (MessagingException | IOException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
    }


    public void sendSimpleHtmlMail(String to, String body, String subject) {
        setupParametersForMessage(to, subject, body, FinalValueUtil.EMAIL_CONTENT_TYPE_HTML);
    }

    public void sendSimplePlainMail(String to, String body, String subject) {
        setupParametersForMessage(to, subject, body, FinalValueUtil.EMAIL_CONTENT_TYPE_PLAIN);
    }
    public void sendErrorMail(String error) {
        String mailBody = "" +
                "<br/>" + timeNow +
                "<br/><br/>" + error +
                "<br/>";
        setupParametersForMessage(FinalValueUtil.EMAIL_SUPPORT, "Error " + timeNow, mailBody, FinalValueUtil.EMAIL_CONTENT_TYPE_HTML);
    }

    public void addAttachment(File file) {
        this.attachments.put(UUID.randomUUID().toString(), file);
    }

}