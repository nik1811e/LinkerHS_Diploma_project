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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = "/editresource")
public class ResourceEditing extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ResourceEditing.class);
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String uuidCourse = req.getParameter("uuidCourse");
        String uuidSection = req.getParameter("uuidSection");
        String uuidResource = req.getParameter("uuidResource");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            req.setCharacterEncoding("UTF-8");
            Transaction transaction = session.beginTransaction();
           MethodUtil.updateJsonStructure(session, transaction, uuidCourse, prepareEditResource(session, uuidCourse, uuidSection, uuidResource, req.getParameter("author"),
                    req.getParameter("desc"), req.getParameter("link"), req.getParameter("name"), Integer.parseInt(req.getParameter("category"))));
                resp.sendRedirect("/pages/resource.jsp?uuidAuth=" + req.getParameter("uuidAuth") + "&&uuidCourse=" + uuidCourse + "&&uuidSection=" + uuidSection + "&&uuidResource=" + uuidResource);
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());

        }

    }

    private String prepareEditResource(Session session, String uuidCourse, String uuidSection, String uuidResource, String author, String desc, String link, String name, int category) {
        CourseStructureTO courseStructureTOgson = gson.fromJson(MethodUtil.getJsonCourseStructure(session,uuidCourse), CourseStructureTO.class);
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
                } else {
                    if (MethodUtil.isUniqueResource(name, link, uuidSection, uuidCourse,anAList.getUuidResource())) {
                        anAList.setAuthor(author);
                        anAList.setCategory_link(category);
                        anAList.setDescriptionResource(desc);
                        anAList.setLink(link);
                        anAList.setName(name);
                        res.add(anAList);
                    } else {
                        return null;
                    }
                }
            }
        }
        MethodUtil.setSectionResources(uuidSection,sectionTOList,tmpSectionList,res);
        courseStructureTOgson.setSection(tmpSectionList);
        return gson.toJson(courseStructureTOgson);
    }
}
