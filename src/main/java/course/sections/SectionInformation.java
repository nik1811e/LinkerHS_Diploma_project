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
    private static final Logger logger = Logger.getLogger(SectionInformation.class);

    public List<SectionTO> getCourseSection(String uuidCourse) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            CourseStructureTO courseStructureTO = new Gson().fromJson(MethodUtil.getJsonCourseStructure(session, uuidCourse), CourseStructureTO.class);
            return new ArrayList<>(courseStructureTO.getSection());
        } catch (Exception ex) {
            return null;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    public SectionTO getSectionInformation(String uuidCourse, String uuidSection) {
        logger.info("getSectionInformation");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            List<SectionTO> sectionTOList = getCourseSection(uuidCourse);
            for (SectionTO st : sectionTOList) {
                if (st.getUuidSection().equals(uuidSection)) {
                    return st;
                }
            }
        } catch (Exception ex) {
            return null;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

}
