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

public class MailUtil {
    private static final Logger LOGGER = Logger.getLogger(MailUtil.class);
    private static String timeNow
            = new SimpleDateFormat(FinalValueUtil.PATTERN_FULL_DATE_TIME).format(new Date().getTime());
    private Map<String, File> attachments = new HashMap<>();

    private void setupMessageParameters(String email, String subject, String mailBody) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", FinalValueUtil.EMAIL_HOST);
            props.put("mail.smtp.port", FinalValueUtil.EMAIL_PORT);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Authenticator auth = new Authenticator() {
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
                    attachmentBodyPart.attachFile(new File(String.valueOf(entry.getValue())));
                    multipart.addBodyPart(attachmentBodyPart);
                }

                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(mailBody, FinalValueUtil.EMAIL_CONTENT_TYPE_HTML);
                multipart.addBodyPart(messageBodyPart);

                message.setContent(multipart);
            } else {
                message.setContent(mailBody, FinalValueUtil.EMAIL_CONTENT_TYPE_HTML);
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

    public void sendMail(String to, String body, String subject) {
        setupMessageParameters(FinalValueUtil.EMAIL_SUPPORT, subject, body);
    }

    public void sendErrorMail(String error) {
        String mailBody = "" +
                "<br/>" + timeNow +
                "<br/><br/>" + error +
                "<br/>";
        setupMessageParameters(FinalValueUtil.EMAIL_SUPPORT, "Error " + timeNow, mailBody);
    }

    public void addAttachment(File file) {
        this.attachments.put(UUID.randomUUID().toString(), file);
    }


   /* public void sendMailRegistration(String email, String login, String password_, HttpServletRequest request) {
        try {
            url = new URL(request.getRequestURL().toString());
            String subject = "Successfully registration";
            String mailBody = "<p>Hello,</p>" +
                    "<p>Вы успешно зарегистрировались на Helper Service</p>" +
                    "<p>" +
                    "<b>Ваш логин: </b>" + login + "" +
                    "<br/><b>Ваш пароль: </b>" + password_ + "" +
                    "</p>";

            setupMessageParameters(email, subject, mailBody);
        } catch (MalformedURLException e) {
            LOGGER.error(e.getMessage());
        }
    }*/

   /* public void sendMailCourseRequest(String email, String uuidAuthReq, String uuidAuthOwn, String uuidCourse) {
        try {
            url = new URL(" http://localhost:8080/course.jsp?uuidAuth=" + uuidAuthReq + "&&uuidCourse=" + uuidCourse);
            String subject = "Access to the course is available.";
            String mailBody = "<p>Hello,</p>" +
                    "<p>Вам открыт доступ к курсу</p>" +
                    "<p>" +
                    "<b>Создатель курса: </b>" + Objects.requireNonNull(MethodUtil.getAuthInfByUuid(uuidAuthOwn)).get(0).getLogin() + "" +
                    "<br/><b>Курс: </b>" + MethodUtil.getCourseNameByUuid(uuidCourse) + "" +
                    "<br/><b>Курс по ссылке:  </b>" + url.getPath() + "" +
                    "</p>";
            setupMessageParameters(email, subject, mailBody);
        } catch (MalformedURLException e) {
            LOGGER.error(e.getMessage());
        }
    }
*/

}