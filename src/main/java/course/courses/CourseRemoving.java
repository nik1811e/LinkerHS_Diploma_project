package course.courses;

import entity.AuthInfEntity;
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

@WebServlet(urlPatterns = "/removecourse")
public class CourseRemoving extends HttpServlet {
    private static final Logger logger = Logger.getLogger(CourseRemoving.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            if (removeCourse(req.getParameter("uuidCourse"), session)) {
                resp.sendRedirect("/pages/catalog.jsp?uuidAuth=" + req.getParameter("uuidAuth"));
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getStackTrace());

        } finally {
            if (session.isOpen())
                session.close();
        }

    }

    private boolean removeCourse(String uuidCourse, Session session) {
        try {
            session.createQuery("DELETE FROM " + FinalValueUtil.ENTITY_COURSE + " WHERE uuid =:uuidCourse")
                    .setParameter("uuidCourse", uuidCourse).executeUpdate();
            return true;
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getStackTrace());
            return false;
        }
    }

    public boolean removeAllUserCourses(AuthInfEntity idAuth) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createQuery("DELETE FROM " + FinalValueUtil.ENTITY_COURSE + " WHERE authById =:idAuth")
                    .setParameter("idAuth", idAuth).executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getStackTrace());
            return false;
        }
        finally {
            if(session.isOpen())
                session.close();
        }
    }
}
