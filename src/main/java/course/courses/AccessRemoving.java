package course.courses;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@WebServlet(urlPatterns = "/removeaccess")
public class AccessRemoving extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AccessRemoving.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            resp.setCharacterEncoding("UTF-8");
            Transaction transaction = session.beginTransaction();
            if (deleteAccess(session, transaction, MethodUtil.getIdAuthByUUID(session, req.getParameter("uuidAuth")),
                    new CourseInformation().getCourseInformation(req.getParameter("uuidCourse")).get(0).getId())) {
                resp.sendRedirect("/pages/accesslist.jsp?uuidAuth=" + new CookieUtil(req).getUserUuidFromToken());
            }
        } catch (Exception ex) {
            LOGGER.info(ex.getLocalizedMessage());
            new MailUtil().sendErrorMail(getClass().getName() + Arrays.toString(ex.getStackTrace()));

        }
    }

    public boolean deleteAccess(Session session, Transaction transaction, int idAuth, int idCourse) {
        try {
            session.createQuery("DELETE FROM " + FinalValueUtil.ENTITY_ACCEESS_INFO + " where idAuth=:idAuth and idCourse=:idCourse")
                    .setParameter("idAuth",idAuth).setParameter("idCourse",idCourse).executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
            return false;
        }
    }
}
