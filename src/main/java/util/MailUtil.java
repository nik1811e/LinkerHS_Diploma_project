package util;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

public class MailUtil {
    private static final Logger logger = Logger.getLogger(MailUtil.class);
    private URL url = null;

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
            message.setFrom(new InternetAddress(FinalValueUtil.EMAIL_SUPPORT));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            message.setContent(mailBody, "text/html; charset=utf-8");
            Transport.send(message);
            logger.info("Sent message pojo [" + email + "] successfully.");

        } catch (MessagingException e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    public void sendErrorMailForAdmin(String error) {
        String mailBody = "" +
                "<br/>" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date().getTime()) +
                "<br/>" + error +
                "<br/>";
        setupMessageParameters(FinalValueUtil.EMAIL_SUPPORT, "Error", mailBody);
    }

    public void sendMailRegistration(String email, String login, String password_, HttpServletRequest request) {
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
            logger.error(e.getMessage());
        }
    }

    public void sendMailCourseRequest(String email, String uuidAuthReq, String uuidAuthOwn, String uuidCourse) {
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
            logger.error(e.getMessage());
        }
    }
}