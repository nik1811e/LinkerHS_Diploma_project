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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("SELECT c FROM " + FinalValueUtil.ENTITY_COURSE + " c WHERE authById =:idAuth", CourseEntity.class)
                    .setParameter("idAuth", session.load(AuthInfEntity.class, MethodUtil.getIdAuthByUUID(session, uuidAuth))).getResultList();
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
            return null;
        }
    }

    public CourseEntity getCourseInformation(String uuidCourse) {
        LOGGER.info("getCourseInformationByUuid");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("SELECT c FROM " + FinalValueUtil.ENTITY_COURSE + " c WHERE c.uuid =:uuidCourse", CourseEntity.class)
                    .setParameter("uuidCourse", uuidCourse).list().get(0);
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
            return null;
        }
    }

    public CourseStructureTO getCourseInformationFromJson(String uuidCourse) {
        LOGGER.info("getCourseInformationFromJson");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return gson.fromJson(MethodUtil.getJsonCourseStructure(uuidCourse), CourseStructureTO.class);
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
            return null;
        }
    }
}
