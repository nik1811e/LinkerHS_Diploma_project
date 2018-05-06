package user.profile;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import util.FinalValueUtil;
import util.MethodUtil;
import util.HibernateUtil;
import util.MailUtil;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

@WebServlet(urlPatterns = "/auth")
public class Authorization extends HttpServlet {

    private static final Logger logger = Logger.getLogger(Authorization.class);
    private static final String REDIRECT_INDEX_PAGE = "/pages/index.jsp";
    private static final String REDIRECT_AUTH_PAGE = "/pages/signin.jsp";


    private String type;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.type = MethodUtil.loginOrEmail(req.getParameter("login_or_email")).toLowerCase();
        if (isPasswordValid(req.getParameter("login_or_email"), req.getParameter("password"))) {
            Object[] obj = getUserUuidAndRole(req.getParameter("login_or_email").toLowerCase());
            assert obj != null;
            setAuthCookie(((Object[]) obj[0])[0].toString(), String.valueOf(((Object[]) obj[0])[1]), resp);
            resp.sendRedirect(REDIRECT_INDEX_PAGE);
        } else {
            resp.sendRedirect(REDIRECT_AUTH_PAGE);
        }
    }

    private Object[] getUserUuidAndRole(String loginOrEmail) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            return session.createSQLQuery("select uuid, role from auth_inf where " + type + " = '" +
                    loginOrEmail + "'").list().toArray();
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getLocalizedMessage());
            return null;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    private boolean isPasswordValid(String cred, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            Query query = session.createQuery("SELECT a.password FROM " + FinalValueUtil.ENTITY_AUTH_INFO + " a WHERE " +
                    type + " = :cred").setParameter("cred", cred);
            return !(query.list().isEmpty()) && (password.equals(query.list().get(0).toString()));
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getLocalizedMessage());
            return false;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    private void setAuthCookie(String uuid, String role, HttpServletResponse resp) {
        try {
            String token = Jwts.builder()
                    .setSubject("AuthToken")
                    .claim("uuid", uuid)
                    .claim("role", role)
                    .signWith(SignatureAlgorithm.HS512,
                            FinalValueUtil.COOKIE_KEY.getBytes("UTF-8")
                    )
                    .compact();
            logger.info(getClass().getName() + " token create successfully");

            Cookie cookie = new Cookie(FinalValueUtil.COOKIE_AUTH_NAME, token);
            cookie.setMaxAge(-1); //  the cookie will persist until browser shutdown
            resp.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
    }
}
