package course.resources;

import com.google.gson.Gson;
import course.pojo.CourseStructureTO;
import course.pojo.ResourceTO;
import course.pojo.SectionTO;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.MethodUtil;
import util.HibernateUtil;
import util.MailUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ALL")
@WebServlet(urlPatterns = "/resourceremoving")
public class ResourceRemoving extends HttpServlet implements Serializable {
    private static final Logger logger = Logger.getLogger(ResourceRemoving.class);

    private Session session = null;
    private Transaction transaction = null;
    private String uuidAuth;
    private String uuidSection;
    private String uuidCourse;
    private String errorMessage;
    private String uuidResource;
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        this.uuidAuth = req.getParameter("uuidAuth");
        this.uuidSection = req.getParameter("uuidSectionDel");
        this.uuidCourse = req.getParameter("uuidCourseDel");
        this.uuidResource = req.getParameter("uuidResourceDel");
        try {
            boolean result = MethodUtil.updateJsonStructure(session,transaction,this.uuidCourse,prepareRemoveResource(this.uuidCourse,this.uuidSection,this.uuidResource));
            if (result) {
                resp.sendRedirect("/pages/section.jsp?uuidCourse="+uuidCourse+"&uuidSection="+uuidSection);
            } else {
                PrintWriter printWriter = resp.getWriter();
                printWriter.println(errorMessage);
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getStackTrace());

        }

    }

    private String prepareRemoveResource(String uuidCourse,String uuidSection,String uuidResource){
        CourseStructureTO courseStructureTOgson = gson.fromJson(MethodUtil.getJsonCourseStructure(session,uuidCourse),CourseStructureTO.class);
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

        for (SectionTO sect: sectionTOList) {
            if (sect.getUuidSection().equals(uuidSection)) {
                sect.setResource(res);
                tmpSectionList.add(sect);
            } else {
                tmpSectionList.add(sect);
            }
        }


        courseStructureTOgson.setSection(tmpSectionList);
        return gson.toJson(courseStructureTOgson);
    }
}
