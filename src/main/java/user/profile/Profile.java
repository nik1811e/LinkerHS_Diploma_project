package user.profile;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import util.MailUtil;
import util.MethodUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Arrays;

@WebServlet(urlPatterns = "/editprofile")
public class Profile extends HttpServlet implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(Profile.class);

    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            req.setCharacterEncoding("UTF-8");
            Transaction transaction = session.beginTransaction();
            if (MethodUtil.updateAuthInf(session, transaction,
                    req.getParameter("login"), req.getParameter("password"),
                    req.getParameter("email"), req.getParameter("fname"), req.getParameter("lname"),
                    req.getParameter("bday"), req.getParameter("uuid"), req.getParameter("desc"),
                    req.getParameter("dateReg"))) {
                resp.sendRedirect("/pages/profile.jsp?uuidAuth" + req.getParameter("uuid"));
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
        }
    }
}
