package admin.user;

import course.courses.CourseRemoving;
import entity.AuthInfEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.FinalValueUtil;
import util.HibernateUtil;
import util.MethodUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/userremove")
public class UserRemoving extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UserRemoving.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            req.setCharacterEncoding("UTF-8");
            Transaction transaction = session.beginTransaction();
            if (removeUserAndDepends(session, transaction, req.getParameter("uuidAuth"))) {
                resp.sendRedirect("/pages/admin/tables.jsp");
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
        }
    }

    private boolean removeUserAndDepends(Session session, Transaction transaction, String uuidUser) {
        try {
            new CourseRemoving().removeAllUserCourses(session.load(AuthInfEntity.class,
                    MethodUtil.getIdAuthByUUID(session, uuidUser)));
            session.createQuery("DELETE FROM " + FinalValueUtil.ENTITY_AUTH_INFO + " WHERE uuid =:uuidUser")
                    .setParameter("uuidUser", uuidUser).executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
            return false;
        }
    }

}
