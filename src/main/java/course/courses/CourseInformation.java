package course.courses;

import com.google.gson.Gson;
import course.pojo.CourseStructureTO;
import entity.AuthInfEntity;
import entity.CourseEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import util.FinalValueUtil;
import util.HibernateUtil;
import util.MethodUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.List;

@WebServlet(urlPatterns = "/courseinformation")
public class CourseInformation extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CourseInformation.class);
    private Gson gson = new Gson();

    public List<CourseEntity> getUserCourses(String uuidAuth) {
        LOGGER.debug(getClass().getName() + "getUserCourses");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            return session.createQuery("SELECT c FROM " + FinalValueUtil.ENTITY_COURSE + " c WHERE authById =:idAuth", CourseEntity.class)
                    .setParameter("idAuth", session.load(AuthInfEntity.class, MethodUtil.getIdAuthByUUID(session,uuidAuth))).getResultList();
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    public List<CourseEntity> getCourseInformation(String uuidCourse) {
        LOGGER.info("getCourseInformationByUuid");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            return session.createQuery("SELECT c FROM " + FinalValueUtil.ENTITY_COURSE + " c WHERE c.uuid =:uuidCourse", CourseEntity.class)
                    .setParameter("uuidCourse", uuidCourse).getResultList();
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    public CourseStructureTO getCourseInformationFromJson(String uuidCourse) {
        LOGGER.info("getCourseInformationFromJson");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            return gson.fromJson(MethodUtil.getJsonCourseStructure(session, uuidCourse), CourseStructureTO.class);
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return null;
    }
}
