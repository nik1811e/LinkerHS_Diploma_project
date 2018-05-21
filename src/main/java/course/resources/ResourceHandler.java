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
import java.util.UUID;

@WebServlet(urlPatterns = "/resourcehandler")
public class ResourceHandler extends HttpServlet implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ResourceHandler.class);

    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        String uuidCourse = req.getParameter("uuidCourse");
        String uuidNewResource = UUID.randomUUID().toString();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            req.setCharacterEncoding("UTF-8");
            Transaction transaction = session.beginTransaction();
            if (MethodUtil.updateJsonStructure(session, transaction, uuidCourse, prepareAddResource(session, req.getParameter("name_resource"),
                    req.getParameter("link"), req.getParameter("author"), req.getParameter("desc"), Integer.parseInt(req.getParameter("id_category")),
                    uuidCourse, req.getParameter("uuidSection"), req.getParameter("uuidAuth"), uuidNewResource))
                    ) {
                resp.sendRedirect("/pages/resource.jsp?uuidAuth=" + req.getParameter("uuidAuth") + "&&uuidCourse=" + uuidCourse + "&uuidSection=" + req.getParameter("uuidSection") + "&uuidResource=" + uuidNewResource);
            } else {
                resp.sendRedirect("/pages/sections.jsp?uuidAuth=" + req.getParameter("uuidAuth") + "&&uuidCourse=" + uuidCourse + "&&uuidSection=" + req.getParameter("uuidSection"));
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());

        }
    }

    private String prepareAddResource(Session session, String name, String link, String author, String description, int id_category, String uuidCourse, String uuidSection, String uuidAuth, String uuidNewResource) {
        CourseStructureTO courseStructureTOgson = gson.fromJson(MethodUtil.getJsonCourseStructure(session,uuidCourse), CourseStructureTO.class);
        List<SectionTO> sections = new ArrayList<>(courseStructureTOgson.getSection());
        List<SectionTO> tempSectionList = new ArrayList<>();
        ResourceTO resourceTO = new ResourceTO();

        List<List<ResourceTO>> list = new ArrayList<>();
        for (SectionTO section : sections) {
            list.add(section.getResource());
        }

        List<ResourceTO> res = new ArrayList<>();
        for (List<ResourceTO> aList : list) {
            for (ResourceTO anAList : aList) {
                if (anAList.getUuidSection().equals(uuidSection)) {
                    res.add(anAList);
                }
            }
        }

            if (MethodUtil.isUniqueResource(name, link, uuidSection, uuidCourse,uuidNewResource)) {
                resourceTO.setName(String.valueOf(name).trim());
                resourceTO.setLink(String.valueOf(link).trim());
                resourceTO.setAuthor(String.valueOf(author).trim());
                resourceTO.setDescriptionResource(String.valueOf(description).trim());
                resourceTO.setUuidAuth(String.valueOf(uuidAuth).trim());
                resourceTO.setUuidSection(String.valueOf(uuidSection).trim());
                resourceTO.setUuidResource(uuidNewResource);
                resourceTO.setCategory_link(id_category);
                res.add(resourceTO);

                MethodUtil.setSectionResources(uuidSection, sections, tempSectionList, res);
                courseStructureTOgson.setSection(tempSectionList);
                return gson.toJson(courseStructureTOgson);
        }
        return null;
    }

}