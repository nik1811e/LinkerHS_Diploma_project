package course.courses;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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

@SuppressFBWarnings("HRS_REQUEST_PARAMETER_TO_HTTP_HEADER")
@WebServlet(urlPatterns = "/removecourse")
public class CourseRemoving extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CourseRemoving.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            resp.setCharacterEncoding("UTF-8");
            session.beginTransaction();
            if (removeCourse(req.getParameter("uuidCourse"), session)) {
                resp.sendRedirect("/pages/catalog.jsp?uuidAuth=" + req.getParameter("uuidAuth"));
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());

        }
    }

    private boolean removeCourse(String uuidCourse, Session session) {
        try {
            session.createQuery("DELETE FROM " + FinalValueUtil.ENTITY_COURSE + " WHERE uuid =:uuidCourse")
                    .setParameter("uuidCourse", uuidCourse).executeUpdate();
            return true;
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            return false;
        }
    }

    public void removeAllUserCourses(AuthInfEntity idAuth) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
           Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE FROM " + FinalValueUtil.ENTITY_COURSE + " WHERE authById =:idAuth")
                    .setParameter("idAuth", idAuth).executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
        }
    }
}
