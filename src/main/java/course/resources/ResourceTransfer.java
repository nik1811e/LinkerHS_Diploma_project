/*
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
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = "/resourcetransfer")
public class ResourceTransfer extends HttpServlet implements Serializable {
    private static final Logger logger = Logger.getLogger(ResourceTransfer.class);

    private Session session = null;
    private Transaction transaction = null;

    private String uuidNewSection;
    private String uuidCourse;
    private String uuidResource;

    private Gson gson = new Gson();
    private String errorMessage;

    @Override

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        this.uuidNewSection = req.getParameter("uuidNewSection");
        this.uuidCourse = req.getParameter("uuidCourseTransfer");
        this.uuidResource = req.getParameter("uuidResource");

        try {
            boolean result = MethodUtil.updateJsonStructure(session, transaction, this.uuidCourse, prepareTransferResource(this.uuidCourse, this.uuidResource, this.uuidNewSection));
            if (result) {
                resp.sendRedirect("/pages/sections.jsp?uuidCourse=" + uuidCourse + "&uuidSection=" + uuidNewSection);
            } else {
                PrintWriter printWriter = resp.getWriter();
                printWriter.println(errorMessage);
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getStackTrace());

        }
    }

    private String prepareTransferResource(String uuidCourse, String uuidOldSection, String uuidResource, String uuidNewSection) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            CourseStructureTO courseStructureTOgson = gson.fromJson(MethodUtil.getJsonCourseStructure(session, uuidCourse), CourseStructureTO.class);
            List<SectionTO> sections = new ArrayList<>(courseStructureTOgson.getSection());
            List<SectionTO> tmpSectionList = new ArrayList<>();
            List<ResourceTO> removeResourceList = new ArrayList<>();

            List<List<ResourceTO>> list = new ArrayList<>();


            List<ResourceTO> transferResource = new ArrayList<>();

            for (List<ResourceTO> aList : list) {
                for (ResourceTO anAList : aList) {
                    if (anAList.getUuidResource().equals(uuidResource)) {
                        anAList.setUuidSection(uuidNewSection);
                        transferResource.add(anAList);
                    }
                }
            }

                for (SectionTO sect : sections) {
                    if (sect.getUuidSection().equals(uuidNewSection)) {
                        sect.setResource(transferResource);
                        tmpSectionList.add(sect);
                    }
                    tmpSectionList.add(sect);
                }

                courseStructureTOgson.setSection(tmpSectionList);
                return gson.toJson(courseStructureTOgson);
            } catch(Exception ex){
                new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n\n\n" + Arrays.toString(ex.getStackTrace()));
                logger.error(ex.getLocalizedMessage());
                return null;
            } finally{
                if (session.isOpen())
                    session.close();
            }
        }

    }
*/
