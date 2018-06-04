package admin.course;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import util.FinalValueUtil;
import util.HibernateUtil;
import util.MailUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@WebServlet(urlPatterns = "/removecourseadmin")
public class CourseRemoving extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CourseRemoving.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            if (removeCourse(req.getParameter("uuidCourse"), session)) {
                resp.sendRedirect("/pages/admin/tables.jsp");
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
            return false;
        }
    }
}
