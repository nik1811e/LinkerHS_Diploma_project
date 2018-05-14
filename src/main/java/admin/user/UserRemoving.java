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
import javax.transaction.Transactional;

@WebServlet(urlPatterns = "/removeuser")
public class UserRemoving extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UserRemoving.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            if (removeUserAndDepends(session, transaction, req.getParameter("uuidUser"))) {
                resp.sendRedirect("");
            }
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
        } finally {
            if (session.isOpen())
                session.close();
        }

    }

    @Transactional
    public boolean removeUserAndDepends(Session session, Transaction transaction, String uuidUser) {
        try {
            new CourseRemoving().removeAllUserCourses(session.load(AuthInfEntity.class,
                    MethodUtil.getIdAuthByUUID(session, uuidUser)));
            session.createQuery("DELETE FROM " + FinalValueUtil.ENTITY_AUTH_INFO + " WHERE uuid =:uuidUser")
                    .setParameter("uuidUser", uuidUser).executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
