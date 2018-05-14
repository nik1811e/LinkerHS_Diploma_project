package admin.user;

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
import java.util.Arrays;

@WebServlet(urlPatterns = "/changeperm")
public class ChangePermission extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ChangePermission.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            if (MethodUtil.changePermission(session, transaction,
                    req.getParameter("uuidAuth"), req.getParameter("role"))) {
                resp.sendRedirect("");
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getStackTrace());
        } finally {
            if (session.isOpen())
                session.close();
        }

    }

}
