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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = "/editsection")
public class SectionEditing extends HttpServlet {
    private static final Logger logger = Logger.getLogger(SectionEditing.class);
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String uuidCourse = req.getParameter("uuidCourse");
        String uuidSection = req.getParameter("uuidSection");
        try {
            if (MethodUtil.updateJsonStructure(session, transaction, uuidCourse,
                    prepareEditSection(session, uuidCourse, uuidSection, req.getParameter("nameSection"), req.getParameter("descSection")))) {
                resp.sendRedirect("/pages/section.jsp?uuidAuth="+req.getParameter("uuidAuth")+"&&uuidCourse="+uuidCourse+"&&uuidSection="+uuidSection);
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getStackTrace());

        }

    }

    private String prepareEditSection(Session session, String uuidCourse, String uuidSection, String nameSection, String descriptionSection) {
        CourseStructureTO courseStructure = gson.fromJson(MethodUtil.getJsonCourseStructure(session, uuidCourse), CourseStructureTO.class);
        List<SectionTO> sectionTOList = new ArrayList<>(courseStructure.getSection());
        List<SectionTO> resultSections = new ArrayList<>();
        if (MethodUtil.isUniqueSectionName(uuidCourse, nameSection)) {
            for (SectionTO sect : sectionTOList) {
                if (sect.getUuidSection().equals(uuidSection)) {
                    sect.setName(nameSection);
                    sect.setDescriptionSection(descriptionSection);
                    sect.setDateLastUpdate(new SimpleDateFormat(FinalValueUtil.PATTERN_DATE).format(new Date().getTime()));
                    resultSections.add(sect);
                }{
                    resultSections.add(sect);
                }
            }
            courseStructure.setSection(resultSections);
            return gson.toJson(courseStructure);
        }
        return null;
    }

}
