package course.courses;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import entity.AuthInfEntity;
import entity.CourseEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@SuppressFBWarnings("HRS_REQUEST_PARAMETER_TO_HTTP_HEADER")
@WebServlet(urlPatterns = "/coursehandler")
public class CourseHandler extends HttpServlet implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(CourseHandler.class);
    private String uuidNewCourse;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            req.setCharacterEncoding("UTF-8");
            Transaction transaction = session.beginTransaction();
            if (addCourse(session, transaction, String.valueOf(req.getParameter("name_course").trim()),
                    String.valueOf(req.getParameter("status").trim()), Integer.parseInt(req.getParameter("id_category")),
                    String.valueOf(req.getParameter("desc").trim()),req.getParameter("uuidAuth"))) {
                resp.sendRedirect("/pages/course.jsp?uuidAuth=" + req.getParameter("uuidAuth") + "&&uuidCourse=" + uuidNewCourse);
            }else {
                resp.sendRedirect("/pages/catalog.jsp?uuidAuth="+req.getParameter("uuidAuth"));
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
            new MailUtil().sendErrorMail(getClass().getName() + Arrays.toString(ex.getStackTrace()));
        }
    }


    private boolean addCourse(Session session, Transaction transaction, String name, String status, int idCategory, String desc, String uuidAuth) {
        LOGGER.debug(getClass().getName() + " addCourse");
        int idAuth = MethodUtil.getIdAuthByUUID(session, uuidAuth);
        uuidNewCourse = UUID.randomUUID().toString();
        try {
            if (MethodUtil.isUniqueNameCourse(session, name, session.load(AuthInfEntity.class, idAuth),uuidNewCourse)) {
                CourseEntity courseEntity = new CourseEntity();
                courseEntity.setAuthById(session.load(AuthInfEntity.class, idAuth));
                courseEntity.setCategory(idCategory);
                courseEntity.setStatus(status);
                courseEntity.setUuid(uuidNewCourse);
                courseEntity.setNameCourse(name);
                courseEntity.setStructure(addStructureCourse(uuidAuth, name, desc, status, new SimpleDateFormat(FinalValueUtil.PATTERN_DATE).format(new Date().getTime())));
                session.save(courseEntity);
                transaction.commit();
                return true;
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
            return false;
        }
        return false;
    }

    private String addStructureCourse(String uuid_user, String name_course, String description_course, String
            status, String date) {

        return "{\n" +
                "\t\"uuid_user\": \" " + uuid_user + " \",\n" +
                "\t\"name_course\": \" " + name_course + "  \",\n" +
                "\t\"description_course\": \" " + description_course.trim() + "\",\n" +
                "\t\"date_create\": \" " + date + "  \",\n" +
                "\t\"status\": \"" + status + " \",\n" +
                "\t\"sections\": [\n" +
                "\t]\n" +
                "}";
    }


}