package course.sections;

import com.google.gson.Gson;
import course.pojo.CourseStructureTO;
import course.pojo.ResourceTO;
import course.pojo.SectionTO;
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
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(urlPatterns = "/sectionhandler")
public class SectionHandler extends HttpServlet implements Serializable {
    private static final Logger logger = Logger.getLogger(SectionHandler.class);
    private Session session = null;
    private Transaction transaction = null;
    private String uuidCourse;
    private String errorMessage;
    private Gson gson = new Gson();
    private String uuidNewSection = null;


    public SectionHandler() {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        this.uuidCourse = String.valueOf(req.getParameter("uuidCourse").trim());
        try {
            boolean result = addSection(prepareAddSection(String.valueOf(req.getParameter("name").trim()),
                    uuidCourse, String.valueOf(req.getParameter("description")).trim()), this.uuidCourse);
            if (result) {
                resp.sendRedirect("/pages/section.jsp?uuidAuth="+req.getParameter("uuidAuth")+"&&uuidSection=" + uuidNewSection + "&&uuidCourse=" + uuidCourse);
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getStackTrace());
        }
    }

    private String prepareAddSection(String name, String uuidCourse, String desc) {
        CourseStructureTO courseStructureTOgson = gson.fromJson(MethodUtil.getJsonCourseStructure(session, uuidCourse), CourseStructureTO.class);
        List<SectionTO> sections = new ArrayList<>(courseStructureTOgson.getSection());
        List<ResourceTO> resources = new ArrayList<>();
        SectionTO sectionTO = new SectionTO();
        if (MethodUtil.isUniqueSectionName(uuidCourse, name)) {
            uuidNewSection = UUID.randomUUID().toString();
            sectionTO.setName(name);
            sectionTO.setUuidCourse(uuidCourse);
            sectionTO.setDescriptionSection(desc);
            sectionTO.setUuidSection(uuidNewSection);
            sectionTO.setDateLastUpdate(new SimpleDateFormat(FinalValueUtil.PATTERN_DATE).format(new Date().getTime()));
            sectionTO.setResource(resources);

            sections.add(sectionTO);
            courseStructureTOgson.setSection(sections);
            return gson.toJson(courseStructureTOgson);
        }
        return null;
    }


    private boolean addSection(String jsonStructure, String uuidCourse) {
        try {
            session.createQuery("UPDATE " + FinalValueUtil.ENTITY_COURSE + " c SET c.structure = :newStructure WHERE c.uuid = :uuid")
                    .setParameter("newStructure", jsonStructure).setParameter("uuid", uuidCourse).executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getStackTrace());
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

}
