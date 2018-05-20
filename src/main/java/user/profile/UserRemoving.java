/*
package user.profile;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.FinalValueUtil;
import util.HibernateUtil;
import util.MailUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@WebServlet(urlPatterns = "/userremove")
public class UserRemoving extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UserRemoving.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            req.setCharacterEncoding("UTF-8");
            if (removeUser(session, session.beginTransaction(), req.getParameter("uuidAuth"))) {
                resp.sendRedirect("/pages/admin/tables.jsp");
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
        }
    }

    private boolean removeUser(Session session, Transaction transaction, String uuidAuth) {
        try {
            session.createQuery("DELETE FROM " + FinalValueUtil.ENTITY_AUTH_INFO + " WHERE uuid =:uuid")
                    .setParameter("uuid", uuidAuth).executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            return false;
        }
    }
}
*/
