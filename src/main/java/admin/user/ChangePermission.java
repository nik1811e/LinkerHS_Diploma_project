package admin.user;

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

@WebServlet(urlPatterns = "/changeperm")
public class ChangePermission extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ChangePermission.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            req.setCharacterEncoding("UTF-8");
            Transaction transaction = session.beginTransaction();
            if (changePermission(session, transaction,
                    req.getParameter("uuidAuth"),req.getParameter("role"))) {
                resp.sendRedirect("/pages/admin/tables.jsp");
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
        }
    }

    private static boolean changePermission(Session session, Transaction transaction, String uuidAuth, String role) {
        try {
            if (role.equals("admin")) {
                session.createQuery("UPDATE " + FinalValueUtil.ENTITY_AUTH_INFO + " a SET " + " a.role=:role WHERE a.uuid=:uuid")
                        .setParameter("role", FinalValueUtil.ROLE_USER).setParameter("uuid", uuidAuth).executeUpdate();
                transaction.commit();
                return true;
            } else if (role.equals("user")) {
                session.createQuery("UPDATE " + FinalValueUtil.ENTITY_AUTH_INFO + " a SET " + " a.role=:role WHERE a.uuid=:uuid")
                        .setParameter("role", FinalValueUtil.ROLE_ADMIN).setParameter("uuid", uuidAuth).executeUpdate();
                transaction.commit();
                return true;
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            return false;
        }
        return false;
    }
}
