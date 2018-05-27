package admin.category;

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

@WebServlet(urlPatterns = "/removecategoryc")
public class CourseCategoryRemoving extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CourseCategoryHandler.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (removeCategoryCourse(session, transaction, req.getParameter("name"))) {
                resp.sendRedirect("/pages/admin/dashboard.jsp");
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(ex.getLocalizedMessage());
            LOGGER.info(ex.getLocalizedMessage());
        }
    }

    private boolean removeCategoryCourse(Session session, Transaction transaction,String name) {
        try {
            session.createQuery("DELETE FROM " + FinalValueUtil.ENTITY_COURSE_CATEGORY + " WHERE name =:name")
                    .setParameter("name", name).executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
