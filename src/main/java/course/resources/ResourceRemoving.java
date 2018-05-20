package course.resources;

import com.google.gson.Gson;
import course.pojo.CourseStructureTO;
import course.pojo.ResourceTO;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ALL")
@WebServlet(urlPatterns = "/resourceremoving")
public class ResourceRemoving extends HttpServlet implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ResourceRemoving.class);

    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String uuidSection = req.getParameter("uuidSectionDel");
        String uuidCourse = req.getParameter("uuidCourseDel");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            req.setCharacterEncoding("UTF-8");
            Transaction transaction = session.beginTransaction();
            if (MethodUtil.updateJsonStructure(session, transaction, uuidCourse, prepareRemoveResource(session, uuidCourse, uuidSection, req.getParameter("uuidResourceDel")))) {
                resp.sendRedirect("/pages/sections.jsp?uuidAuth=" + req.getParameter("uuidAuth") + "&&uuidCourse=" + uuidCourse + "&uuidSection=" + uuidSection);
            } else {
                resp.sendRedirect("/pages/resource.jsp?uuidAuth=" + req.getParameter("uuidAuth") + "&&uuidCourse=" + uuidCourse + "&uuidSection=" + uuidSection + "&uuidResource=" + req.getParameter("uuidResourceDel"));

            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());

        }

    }

    private String prepareRemoveResource(Session session, String uuidCourse, String uuidSection, String uuidResource) {
        CourseStructureTO courseStructureTOgson = gson.fromJson(MethodUtil.getJsonCourseStructure(uuidCourse), CourseStructureTO.class);
        List<SectionTO> sectionTOList = new ArrayList<>(courseStructureTOgson.getSection());
        List<SectionTO> tmpSectionList = new ArrayList<>();

        List<List<ResourceTO>> list = new ArrayList<>();
        for (SectionTO section : sectionTOList) {
            list.add(section.getResource());
        }

        List<ResourceTO> res = new ArrayList<>();
        for (List<ResourceTO> aList : list) {
            for (ResourceTO anAList : aList) {
                if (!anAList.getUuidResource().equals(uuidResource)) {
                    res.add(anAList);
                }
            }
        }
        MethodUtil.setSectionResources(uuidSection, sectionTOList, tmpSectionList, res);

        courseStructureTOgson.setSection(tmpSectionList);
        return gson.toJson(courseStructureTOgson);
    }
}
