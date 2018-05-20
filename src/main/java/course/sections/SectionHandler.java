package course.sections;

import com.google.gson.Gson;
import course.pojo.CourseStructureTO;
import course.pojo.ResourceTO;
import course.pojo.SectionTO;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressFBWarnings("HRS_REQUEST_PARAMETER_TO_HTTP_HEADER")
@WebServlet(urlPatterns = "/sectionhandler")
public class SectionHandler extends HttpServlet implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(SectionHandler.class);
    private Gson gson = new Gson();

    private String uuidNewSection = null;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String uuidCourse = String.valueOf(req.getParameter("uuidCourse").trim());
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            req.setCharacterEncoding("UTF-8");
            String uuidAuth = req.getParameter("uuidAuth");
            Transaction transaction = session.beginTransaction();
            if (addSection(session, transaction, prepareAddSection(session, String.valueOf(req.getParameter("name").trim()),
                    uuidCourse, String.valueOf(req.getParameter("description")).trim()), uuidCourse)) {
                resp.sendRedirect("/pages/sections.jsp?uuidAuth=" + uuidAuth + "&&uuidSection=" + uuidNewSection + "&&uuidCourse=" + uuidCourse);
            } else {
                resp.sendRedirect("/pages/course.jsp?uuidAuth=" + uuidAuth + "&&uuidCourse=" + uuidCourse);
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
        }
    }

    private String prepareAddSection(Session session, String name, String uuidCourse, String desc) {
        CourseStructureTO courseStructureTOgson = gson.fromJson(MethodUtil.getJsonCourseStructure(uuidCourse), CourseStructureTO.class);
        List<SectionTO> sections = new ArrayList<>(courseStructureTOgson.getSection());
        List<ResourceTO> resources = new ArrayList<>();
        SectionTO sectionTO = new SectionTO();
        uuidNewSection = UUID.randomUUID().toString();
        if (MethodUtil.isUniqueSectionName(uuidCourse, name, uuidNewSection)) {
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

    private boolean addSection(Session session, Transaction transaction, String jsonStructure, String uuidCourse) {
        try {
            if (!jsonStructure.isEmpty()) {
                session.createQuery("UPDATE " + FinalValueUtil.ENTITY_COURSE + " c SET c.structure = :newStructure WHERE c.uuid = :uuid")
                        .setParameter("newStructure", jsonStructure).setParameter("uuid", uuidCourse).executeUpdate();
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

}
