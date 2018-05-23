package user.profile;

import com.google.gson.Gson;
import entity.AuthInfEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import user.IParseJsonString;
import util.FinalValueUtil;
import util.HibernateUtil;
import util.MailUtil;
import util.ReCaptchaUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@WebServlet(urlPatterns = "/registration")
public class Registration extends HttpServlet implements IParseJsonString {
    private static final Logger LOGGER = Logger.getLogger(Registration.class);

    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("UTF-8");
            if (ReCaptchaUtil.verify(req.getParameter("g-recaptcha-response"))) {
                if (doRegistration(req.getParameter("login"), req.getParameter("password"),
                        req.getParameter("email"), req.getParameter("fname"),
                        req.getParameter("lname"), req.getParameter("bday"))) {
                    URL url = new URL(req.getRequestURL().toString());
                    String login = req.getParameter("login");
                    String body = "<br/> " + new SimpleDateFormat(FinalValueUtil.PATTERN_FULL_DATE_TIME).format(new Date().getTime()) + "<br/>" +
                            "<p>Здравствуйте,</p>" +
                            "<p>Вы успешно зарегистрировались на Helper Service</p>" +
                            "<p>" +
                            "<b>Ваш логин: </b>" + login + "" +
                            "<br/><b>Ваш пароль: </b>" + req.getParameter("password") + "" +
                            "</p>" +
                            "<p>Ваш профиль: <a href=\"" + url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/pages/profile.jsp?login=" + login + "\">" +
                            "" + url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/pages/profile.jsp?user=" + login + "</a></p>";

                    String subject = "Успешная регистрация";

                    new MailUtil().sendMail(req.getParameter("email"), body, subject);

                    resp.sendRedirect("/pages/index.jsp");
                }
            }
        } catch (IOException e) {
            new MailUtil().sendErrorMail(getClass().getName() + Arrays.toString(e.getStackTrace()));
        }
    }

    public boolean doRegistration(String login, String password, String email, String first_name, String last_name, String dbay) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LOGGER.debug(this.getClass().getName() + ", method: doRegistration");
            session.beginTransaction();
            AuthInfEntity authInfoEntity = gson.fromJson(prepareInputString(login.toLowerCase(), password.toLowerCase(), email.toLowerCase()), AuthInfEntity.class);
            if (isLoginAndEmailEmpty(session, authInfoEntity.getLogin().toLowerCase(), authInfoEntity.getEmail().toLowerCase())) {
                String uuidAuth = UUID.randomUUID().toString();
                authInfoEntity.setLogin(authInfoEntity.getLogin().toLowerCase());
                authInfoEntity.setPassword(authInfoEntity.getPassword());
                authInfoEntity.setEmail(authInfoEntity.getEmail());
                authInfoEntity.setRole(FinalValueUtil.ROLE_USER);
                authInfoEntity.setUuid(uuidAuth);
                authInfoEntity.setDateReg(new SimpleDateFormat(FinalValueUtil.PATTERN_DATE).format(new java.util.Date().getTime()));
                authInfoEntity.setFName(first_name);
                authInfoEntity.setLName(last_name);
                authInfoEntity.setBDay(dbay);
                authInfoEntity.setAbout("Пусто");
                authInfoEntity.setNameImage("пусто");
                authInfoEntity.setRequest("{\"uuid_course_owner\":\" " + uuidAuth + " \",\"request\":[]}");
                session.save(authInfoEntity);
                session.getTransaction().commit();
                session.close();
                return true;
            } else {
                LOGGER.debug("Login isn't empty");
                return false;
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
            new MailUtil().sendErrorMail(getClass().getName() + Arrays.toString(ex.getStackTrace()));
            return false;
        }
    }

    private boolean isLoginAndEmailEmpty(Session session, String login, String email) {
        return session.createQuery("SELECT a.login, a.email FROM " +
                FinalValueUtil.ENTITY_AUTH_INFO + " a WHERE a.login = :login OR a.email = :email")
                .setParameter("login", login)
                .setParameter("email", email).list().isEmpty();
    }

    @Override
    public String prepareInputString(String login, String password, String email) {
        AuthInfEntity authInfoEntity = new AuthInfEntity();
        authInfoEntity.setLogin(login);
        authInfoEntity.setPassword(password);
        authInfoEntity.setEmail(email);
        return gson.toJson(authInfoEntity);
    }

}