package course.sections;

import com.google.gson.Gson;
import course.pojo.CourseStructureTO;
import course.pojo.SectionTO;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import util.HibernateUtil;
import util.MethodUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/sectioninformation")
public class SectionInformation extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(SectionInformation.class);

    public List<SectionTO> getCourseSection(String uuidCourse) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            CourseStructureTO courseStructureTO = new Gson().fromJson(MethodUtil.getJsonCourseStructure(session, uuidCourse), CourseStructureTO.class);
            return new ArrayList<>(courseStructureTO.getSection());
        } catch (Exception ex) {
            return null;
        }
    }

    public SectionTO getSectionInformation(String uuidCourse, String uuidSection) {
        LOGGER.info("getSectionInformation");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            List<SectionTO> sectionTOList = getCourseSection(uuidCourse);
            for (SectionTO st : sectionTOList) {
                if (st.getUuidSection().equals(uuidSection)) {
                    return st;
                }
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

}
