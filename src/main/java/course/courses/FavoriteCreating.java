package course.courses;

import entity.FavoriteCourseEntity;
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

@WebServlet(urlPatterns = "/addtofavorite")
public class FavoriteCreating extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(FavoriteCreating.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (addToFavorite(session, transaction, MethodUtil.getIdCourseByUUID(session, req.getParameter("uuidCourse")),
                    MethodUtil.getIdAuthByUUID(session, req.getParameter("uuidAuth"))))
                resp.sendRedirect("/pages/course.jsp?uuidAuth="+req.getParameter("uuidAuth")+"&&uuidCourse="+req.getParameter("uuidCourse"));
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
        }
    }

    private boolean addToFavorite(Session session, Transaction transaction, int id_course, int id_auth) {
        LOGGER.debug(getClass().getName() + " addToFavorite");
        try {
            FavoriteCourseEntity favoriteCourseEntity = new FavoriteCourseEntity();
            favoriteCourseEntity.setIdAuth(id_auth);
            favoriteCourseEntity.setIdCourse(id_course);
            session.save(favoriteCourseEntity);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
            return false;
        }
    }
}
