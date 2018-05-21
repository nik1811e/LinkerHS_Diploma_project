package course.sections;

import com.google.gson.Gson;
import course.pojo.CourseStructureTO;
import course.pojo.SectionTO;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = "/sectionremoving")
public class SectionRemoving extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(SectionRemoving.class);
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String uuidCourse = req.getParameter("uuidCourse");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            req.setCharacterEncoding("UTF-8");
            Transaction transaction = session.beginTransaction();
            if (MethodUtil.updateJsonStructure(session, transaction, uuidCourse, prepareRemoveSection(uuidCourse,
                    req.getParameter("uuidSection"), session))) {
                resp.sendRedirect("/pages/course.jsp?uuidAuth=" + req.getParameter("uuidAuth") + "&&uuidCourse=" + uuidCourse);
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());

        }

    }

    private String prepareRemoveSection(String uuidCourse, String uuidSection, Session session) {

        CourseStructureTO courseStructureTOgson = gson.fromJson(MethodUtil.getJsonCourseStructure(session,uuidCourse), CourseStructureTO.class);
        List<SectionTO> sectionTOList = new ArrayList<>(courseStructureTOgson.getSection());
        List<SectionTO> resultSectionList = new ArrayList<>();

        for (SectionTO sect : sectionTOList) {
            if (!sect.getUuidSection().equals(uuidSection))
                resultSectionList.add(sect);
        }
        courseStructureTOgson.setSection(resultSectionList);
        return gson.toJson(courseStructureTOgson);
    }

/*
    private boolean removeSection(String uuidCourse, String newStructure, Session session, Transaction transaction) {
        try {
            session.createQuery("UPDATE " + FinalValueUtil.ENTITY_COURSE + " SET structure =:newStructure")
                    .setParameter("newStructure", newStructure).executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            return false;
        }
    }
*/
}
