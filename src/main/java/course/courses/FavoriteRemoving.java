package course.courses;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.FinalValueUtil;
import util.HibernateUtil;
import util.MailUtil;
import util.MethodUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@WebServlet(urlPatterns = "/removefromfavorite")
public class FavoriteRemoving extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(FavoriteRemoving.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (removeFromFavorite(session, transaction, MethodUtil.getIdCourseByUUID(session, req.getParameter("uuidCourse")), MethodUtil.getIdAuthByUUID(session, req.getParameter("uuidAuth"))))
            {
                if(req.getParameter("from").equals("course")){
                    resp.sendRedirect("/pages/course.jsp?uuidAuth="+req.getParameter("uuidAuth")+"&&uuidCourse="+req.getParameter("uuidCourse"));
                }else{
                    resp.sendRedirect("/pages/favorite.jsp?uuidAuth="+req.getParameter("uuidAuth"));
                }
            }
        } catch (Exception ex) {
            LOGGER.info(ex.getLocalizedMessage());
            new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
        }
    }

    private boolean removeFromFavorite(Session session, Transaction transaction, int id_course, int id_auth) {
        try {
            session.createQuery("DELETE FROM " + FinalValueUtil.ENTITY_FAVORITE_COURSE + " WHERE idCourse =:idCourse AND idAuth=:idAuth")
                    .setParameter("idCourse", id_course).setParameter("idAuth", id_auth).executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception ex) {
            LOGGER.info(ex.getLocalizedMessage());
            return false;
        }

    }
}
