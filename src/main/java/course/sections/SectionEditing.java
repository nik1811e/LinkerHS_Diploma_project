package course.sections;

import com.google.gson.Gson;
import course.pojo.CourseStructureTO;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = "/editsection")
public class SectionEditing extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(SectionEditing.class);
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uuidCourse = req.getParameter("uuidCourse");
        String uuidSection = req.getParameter("uuidSection");
        String path = "/pages/section.jsp?uuidAuth=" + req.getParameter("uuidAuth") + "&&uuidCourse=" + uuidCourse + "&&uuidSection=" + uuidSection;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            req.setCharacterEncoding("UTF-8");
            Transaction transaction = session.beginTransaction();
            if (MethodUtil.updateJsonStructure(session, transaction, uuidCourse,
                    prepareEditSection(session, uuidCourse, uuidSection,
                            req.getParameter("nameSection"), req.getParameter("descSection")))) {
                resp.sendRedirect(path);
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            resp.sendRedirect(path);
        }

    }

    private String prepareEditSection(Session session, String uuidCourse, String uuidSection, String nameSection, String descriptionSection) throws Exception {
        CourseStructureTO courseStructure = gson.fromJson(MethodUtil.getJsonCourseStructure(session,uuidCourse), CourseStructureTO.class);
        List<SectionTO> sectionTOList = new ArrayList<>(courseStructure.getSection());
        if (MethodUtil.isUniqueSectionName(uuidCourse, nameSection, uuidSection)) {
            for (SectionTO sect : sectionTOList) {
                if (sect.getUuidSection().equals(uuidSection)) {
                    sect.setName(nameSection);
                    sect.setDescriptionSection(descriptionSection);
                    sect.setDateLastUpdate(new SimpleDateFormat(FinalValueUtil.PATTERN_DATE).format(new Date().getTime()));
                }
            }
            courseStructure.setSection(sectionTOList);
            return gson.toJson(courseStructure);
        }
        return gson.toJson(courseStructure);

    }

}
