package user.profile;

import com.google.gson.Gson;
import entity.AuthInfEntity;
import org.apache.log4j.Logger;
import org.hibernate.Query;
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
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.UUID;

@WebServlet(urlPatterns = "/registration")
public class Registration extends HttpServlet implements IParseJsonString {
    private static final Logger logger = Logger.getLogger(Registration.class);

    private Session session;
    private Gson gson;
    private String errorMessage;

    public Registration() {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();
        if (ReCaptchaUtil.verify(req.getParameter("g-recaptcha-response"))) {
            if (doRegistration(req.getParameter("login"), req.getParameter("password"),
                    req.getParameter("email"), req.getParameter("fname"),
                    req.getParameter("lname"), req.getParameter("bday"))) {
                try {
                    new MailUtil().sendMailRegistration(req.getParameter("email"),
                            req.getParameter("login"),
                            req.getParameter("password"), req);
                    resp.sendRedirect("/pages/index.jsp");
                } catch (IOException e) {
                    new MailUtil().sendErrorMailForAdmin(getClass().getName() + Arrays.toString(e.getStackTrace()));
                }
            }
        } else {
            errorMessage = "You missed the Captcha";
            printWriter.println("<font color=red>" + errorMessage + "</font>");
        }
    }

    public boolean doRegistration(String login, String password, String email, String first_name, String last_name, String dbay) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            logger.debug(this.getClass().getName() + ", method: doRegistration");

            AuthInfEntity authInfoEntity = gson.fromJson(prepareInputString(login.toLowerCase(), password.toLowerCase(), email.toLowerCase()), AuthInfEntity.class);
            if (isLoginAndEmailEmpty(authInfoEntity.getLogin().toLowerCase(), authInfoEntity.getEmail().toLowerCase())) {
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
                authInfoEntity.setRequest("{\"uuid_course_owner\":\" " + uuidAuth + " \",\"request\":[]}");
                session.save(authInfoEntity);
                session.getTransaction().commit();
                session.close();
                return true;
            } else {
                errorMessage = "Login isn't empty";
                logger.debug("Login isn't empty");
                return false;
            }


        } catch (Exception ex) {
            return false;
        } finally {
            if (session.isOpen())
                session.close();
        }

    }

    private boolean isLoginAndEmailEmpty(String login, String email) {
        Query query = session.createQuery("SELECT a.login, a.email FROM " +
                FinalValueUtil.ENTITY_AUTH_INFO + " a WHERE a.login = :login OR a.email = :email");
        query.setParameter("login", login);
        query.setParameter("email", email);
        return query.list().isEmpty();
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